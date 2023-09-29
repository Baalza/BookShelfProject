package com.bookshelf2.demo.service;

import com.bookshelf2.demo.model.BookshelfUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
@Service //non ha transactional perchè legge e basta
public class BookshelfUserDetailsContextMapper implements UserDetailsContextMapper {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DataSource dataSource;

    private static final String loadUserByUsernameQuery = "select username, " +
            "password, enabled, nickname " + "from users where username = ?";

    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        final BookshelfUserDetails userDetails = new BookshelfUserDetails(ctx.getStringAttribute("uid"),
                "fake", Collections.EMPTY_LIST);

        jdbcTemplate.queryForObject(loadUserByUsernameQuery, new RowMapper<BookshelfUserDetails>() {

            @Override
            public BookshelfUserDetails mapRow(ResultSet resultSet, int i)throws SQLException{
                userDetails.setNickname(resultSet.getString("nickname"));
                return userDetails;
            }
        }, ctx.getStringAttribute("uid"));

        return userDetails;
    }

    @Override
    public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {

    }
}
