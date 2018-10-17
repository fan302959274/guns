package TTT;

import com.stylefeng.guns.rest.common.persistence.model.AuthUser;
import com.stylefeng.guns.rest.common.persistence.dao.AuthUserMapper;
import TTT.IAuthUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-10-17
 */
@Service
public class AuthUserServiceImpl extends ServiceImpl<AuthUserMapper, AuthUser> implements IAuthUserService {
	
}
