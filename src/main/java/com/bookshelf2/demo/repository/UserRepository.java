package com.bookshelf2.demo.repository;

import com.bookshelf2.demo.model.User;
//import com.bookshelf2.demo.model.Verificationtoken;
import com.bookshelf2.demo.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    String userReport(String username);

    List<String> findAllNotAdmin();

   User findByUsername(String username);

    void deleteAut (long id);

    @Transactional
    @Modifying
    @Query("delete from Authorities a where a.id = ?1 ")
    void deleteAut2(long id);
    //void saveToken(Verificationtoken verificationToken);
}
