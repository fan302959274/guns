package TTT;

import com.stylefeng.guns.rest.common.persistence.model.PkAttachment;
import com.stylefeng.guns.rest.common.persistence.dao.PkAttachmentMapper;
import TTT.IPkAttachmentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 队员附件表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-10-17
 */
@Service
public class PkAttachmentServiceImpl extends ServiceImpl<PkAttachmentMapper, PkAttachment> implements IPkAttachmentService {
	
}
