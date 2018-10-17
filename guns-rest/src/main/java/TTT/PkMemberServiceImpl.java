package TTT;

import com.stylefeng.guns.rest.common.persistence.model.PkMember;
import com.stylefeng.guns.rest.common.persistence.dao.PkMemberMapper;
import TTT.IPkMemberService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 队员表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-10-17
 */
@Service
public class PkMemberServiceImpl extends ServiceImpl<PkMemberMapper, PkMember> implements IPkMemberService {
	
}
