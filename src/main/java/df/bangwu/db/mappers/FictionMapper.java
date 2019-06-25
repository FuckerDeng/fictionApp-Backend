package df.bangwu.db.mappers;

import df.bangwu.bean.Fiction;
import df.bangwu.bean.FictionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FictionMapper {
    long countByExample(FictionExample example);

    int deleteByExample(FictionExample example);

    int insert(Fiction record);

    int insertSelective(Fiction record);

    List<Fiction> getTop20();

    List<Fiction> selectByExample(FictionExample example);

    int updateByExampleSelective(@Param("record") Fiction record, @Param("example") FictionExample example);

    int updateByExample(@Param("record") Fiction record, @Param("example") FictionExample example);
}