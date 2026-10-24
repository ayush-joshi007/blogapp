package com.scaler.bloggapp.articles;

import com.scaler.bloggapp.articles.dtos.CreateArticleRequest;
import com.scaler.bloggapp.articles.dtos.UpdateArticleRequest;
import com.scaler.bloggapp.users.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class ArticlesService {

    private final ArticlesRepository articlesRepository;
    private final UsersRepository usersRepository;

    public ArticlesService(ArticlesRepository articlesRepository, UsersRepository usersRepository) {
        this.articlesRepository = articlesRepository;
        this.usersRepository = usersRepository;
    }

    public Iterable<ArticleEntity> getAllArticles() {
        return articlesRepository.findAll();
    }

    public ArticleEntity getArticleBySlug(String slug){
        var article = articlesRepository.findBySlug(slug);
        if(article == null) {
            throw new ArticleNotFoundException(slug);
        }
        return article;
    }

    public ArticleEntity createArticle(CreateArticleRequest a, Long authorId) {
        var author = usersRepository.findById(authorId).orElseThrow(() -> new IllegalArgumentException("Author not found with id: " + authorId));
        return articlesRepository.save(
                ArticleEntity.builder()
                        .title(a.getTitle())
                        //todo: create a proper slugification function
                        .slug(a.getTitle().toLowerCase().replaceAll("\\s+", "-"))
                        .subtitle(a.getSubtitle())
                        .body(a.getBody())
                        .author(author)
                        .build()
        );
    }

    public ArticleEntity updateArticle(Long articleId, UpdateArticleRequest a){
        var article = articlesRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));
        if(a.getTitle() != null) {
            article.setTitle(a.getTitle());
            article.setSlug(a.getTitle().toLowerCase().replaceAll("\\s+", "-"));
        }
        if(a.getSubtitle() != null) {
            article.setSubtitle(a.getSubtitle());
        }
        if(a.getBody() != null) {
            article.setBody(a.getBody());
        }
        return articlesRepository.save(article);
    }


    static class ArticleNotFoundException extends IllegalArgumentException {
        public ArticleNotFoundException(String slug) {
            super("Article with slug: " + slug + " not found.");
        }
        public ArticleNotFoundException(Long id){
            super("Article with id: " + id + " not found.");
        }

    }
}
