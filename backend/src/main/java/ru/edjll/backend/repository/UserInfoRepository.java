package ru.edjll.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.edjll.backend.dto.userInfo.UserInfoDetailDto;
import ru.edjll.backend.dto.userInfo.UserInfoDto;
import ru.edjll.backend.entity.UserInfo;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    @Query( "select new ru.edjll.backend.dto.userInfo.UserInfoDto(" +
                "user.id, user.firstName, user.lastName, user.username, " +
                "case when userInfo is null then null else userInfo.birthday end, " +
                "case when city is null then null else city.title end) " +
            "from User user left join user.userInfo userInfo left join userInfo.city city " +
            "where user.enabled = true and user.username = :username" )
    Optional<UserInfoDto> getUserInfoByUsername(@Param("username") String username);

    @Query( "select new ru.edjll.backend.dto.userInfo.UserInfoDetailDto(" +
                "user.id, user.firstName, user.lastName, user.username, " +
                "case when userInfo is null then null else userInfo.birthday end, " +
                "case when city is null then null else city.id end, " +
                "case when city is null then null else city.title end, " +
                "case when country is null then null else country.id end, " +
                "case when country is null then null else country.title end) " +
            "from User user left join user.userInfo userInfo left join userInfo.city city left join city.country country  " +
            "where user.username = :username" )
    UserInfoDetailDto getUserInfoDetailByUsername(@Param("username") String username);
}
