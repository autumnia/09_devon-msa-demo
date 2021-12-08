package devonboot.poc.membership.repository;

import devonboot.poc.model.Membership;
import devonboot.poc.util.AutoMapRowMapper;
import devonframe.dataaccess.CommonDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

@Slf4j
@Repository
public class MembershipRepository {

    private CommonDao commonDao;
    public MembershipRepository(CommonDao commonDao) {
        this.commonDao = commonDao;
    }

    public Membership retrieveMembershipByUserId(String userId) {
        return commonDao.select("retrieveMembershipByUserId", userId);
    }

    public Membership retrieveMembershipById(String id) {
        return commonDao.select("retrieveMembershipById", id);
    }

    public int updateMembership(Membership membership) {
        return commonDao.update("updateMembership", membership);
    }
}
