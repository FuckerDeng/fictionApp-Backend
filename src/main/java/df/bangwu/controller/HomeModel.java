package df.bangwu.controller;

import df.bangwu.bean.Fiction;
import df.bangwu.db.Dao;
import df.bangwu.db.mappers.FictionMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HomeModel {

    @ResponseBody
    @RequestMapping("/getLunBo")
    public String getLunBo(){
        System.out.println("接收到请求！");
        return                 "../../static/imgs/muwu.jpg,../../static/imgs/qrcode.png,../../static/imgs/shuijiao.jpg";
    }
    @ResponseBody
    @RequestMapping("/getTop20")
    public List<Fiction> getTop20(HttpServletRequest request){
        System.out.println(request.getRequestURL());
        SqlSession sqlSession = Dao.getSqlSession();
        FictionMapper mapper = sqlSession.getMapper(FictionMapper.class);
        List<Fiction> top20 = mapper.getTop20();
        Dao.closeSession(sqlSession);
        return top20;
    }
}
