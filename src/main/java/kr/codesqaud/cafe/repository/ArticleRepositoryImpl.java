package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import kr.codesqaud.cafe.domain.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * H2 DB에 JdbcTemplate을 사용하여 게시글 데이터를 저장하는 저장소
 */
@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

    private final JdbcTemplate template;

    public ArticleRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Article save(Article article) {
        String sql = "insert into articles(writer, title, contents) values(?, ?, ?)";
        template.update(sql, article.getWriter(), article.getTitle(), article.getContents());
        return article;
    }

    @Override
    public Optional<Article> findBySequence(long sequence) {
        String sql = "select * from articles where sequence = ?";
        return Optional.ofNullable(template.queryForObject(sql, articleRowMapper(), sequence));
    }

    @Override
    public List<Article> findAll() {
        String sql = "select * from articles";
        return template.query(sql, articleRowMapper());
    }

    private RowMapper<Article> articleRowMapper() {
        return (resultSet, rowNumber) -> new Article(
                resultSet.getString("writer"),
                resultSet.getString("title"),
                resultSet.getString("contents")
        );
    }
}
