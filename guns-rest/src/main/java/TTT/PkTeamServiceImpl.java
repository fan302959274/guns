package TTT;

import com.stylefeng.guns.rest.common.persistence.model.PkTeam;
import com.stylefeng.guns.rest.common.persistence.dao.PkTeamMapper;
import TTT.IPkTeamService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 球队表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-10-17
 */
@Service
public class PkTeamServiceImpl extends ServiceImpl<PkTeamMapper, PkTeam> implements IPkTeamService {
	
}
