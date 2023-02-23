package com.ebaykorea.payback.infrastructure.persistence.repository.customer;

import com.ebaykorea.payback.infrastructure.persistence.repository.customer.entity.SmilePointTradeEntity;
import com.ebaykorea.saturn.mssql.dbname.Gmkt;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedure;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedureParameter;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.time.Instant;
import java.util.List;


@Repository
@SaturnDataSource(name = Gmkt.TIGER_WRITE)
public class SmilePointTradeRepository {

    @SaturnProcedure(
            procedureName = SmilePointTradeEntity.SAVE,
            parameters = {
                    @SaturnProcedureParameter(name = "cust_no", sqlType = Types.VARCHAR, scale = 10),
                    @SaturnProcedureParameter(name = "point", sqlType = Types.INTEGER),
                    @SaturnProcedureParameter(name = "reason_cd", sqlType = Types.INTEGER),
                    @SaturnProcedureParameter(name = "contr_no", sqlType = Types.BIGINT),
                    @SaturnProcedureParameter(name = "comment", sqlType = Types.VARCHAR, scale = 100),
                    @SaturnProcedureParameter(name = "ers_no", sqlType = Types.INTEGER),
                    @SaturnProcedureParameter(name = "eid", sqlType = Types.INTEGER),
                    @SaturnProcedureParameter(name = "appr_status", sqlType = Types.INTEGER),
                    @SaturnProcedureParameter(name = "target_type", sqlType = Types.INTEGER),
                    @SaturnProcedureParameter(name = "win_no", sqlType = Types.BIGINT),
                    @SaturnProcedureParameter(name = "SELLER_ID", sqlType = Types.VARCHAR, scale = 10)
            },
            throwEx = true //입력 실패시 exception 발생
    )
    public long save(String custNo,
                     int point,
                     int reasonCode,
                     long contrNo,
                     String comment,
                     int ersNo,
                     int eId,
                     int apprStaus,
                     int targetType,
                     long winNo,
                     String sellerId
    ) {
        return 0;
    }


    @SaturnProcedure(
            procedureName = SmilePointTradeEntity.SelectBySmilePayNo,
            parameters = {
                    @SaturnProcedureParameter(name = "smilepay_no", sqlType = Types.BIGINT)
            },
            throwEx = true //입력 실패시 exception 발생
    )
    public SmilePointTradeEntity SelectBySmilePayNo(long smilePayNo) {
        return null;
    }

    @SaturnProcedure(
            procedureName = SmilePointTradeEntity.SelectByContrNo,
            parameters = {
                    @SaturnProcedureParameter(name = "cust_no", sqlType = Types.VARCHAR, scale = 10),
                    @SaturnProcedureParameter(name = "contr_no", sqlType = Types.BIGINT)
            },
            throwEx = true //입력 실패시 exception 발생
    )
    public List<SmilePointTradeEntity> SelectByContrNo(String buyerNo, long contrNo) {
        return null;
    }

    @SaturnProcedure(
            procedureName = SmilePointTradeEntity.SelectHistory,
            parameters = {
                    @SaturnProcedureParameter(name = "cust_no", sqlType = Types.VARCHAR, scale = 10),
                    @SaturnProcedureParameter(name = "start_date", sqlType = Types.DATE),
                    @SaturnProcedureParameter(name = "end_date", sqlType = Types.DATE),
                    @SaturnProcedureParameter(name = "MAX_COUNT", sqlType = Types.INTEGER)
            },
            throwEx = true //입력 실패시 exception 발생
    )
    public List<SmilePointTradeEntity> SelectHistory(String buyerNo, String startDate, String endDate, int maxRowCount) {
        return null;
    }
}
