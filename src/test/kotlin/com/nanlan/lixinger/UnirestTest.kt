package com.nanlan.lixinger

import com.nanlan.lixinger.pojo.RequestFs
import com.nanlan.lixinger.pojo.ResponseFs
import com.nanlan.lixinger.terms.BalanceSheet
import com.nanlan.lixinger.terms.CashFlowSheet
import com.nanlan.lixinger.terms.Metrics
import com.nanlan.lixinger.terms.ProfitSheet
import com.nanlan.util.CoreJsonUtil
import kong.unirest.Unirest
import org.junit.jupiter.api.Test

/**
 * 傻逼了，就试用就可以了
 */
class UnirestTest {

    @Test
    fun test00() {
        val r = Unirest.post("https://open.lixinger.com/api/cn/company/fs/non_financial")
            .header("Content-Type", "application/json")
            .body("{\"token\":\"35a572a3-bd7b-4e35-affa-7338f1cb9489\",\"date\":\"2021-09-30\",\"stockCodes\":[\"300750\",\"600519\"],\"metricsList\":[\"q.ps.toi.t\"]}")
            .asString()
        println(r.body)
    }

    @Test
    fun test01() {
        val reqBody = RequestFs(
            startDate = "2019-12-31",
            endDate = "2021-12-31",
            stockCodes = listOf("300750"),
            metricsList = listOf(
                "y.ps.toi.t",
                "y.ps.op.t",
                "q.ps.toi.t"
            )
        ).toJsonString()

        val r = Unirest.post("https://open.lixinger.com/api/cn/company/fs/non_financial")
            .header("Content-Type", "application/json")
            .body(
                reqBody
            ).asString()
        val reponse = CoreJsonUtil.parse<ResponseFs>(r.body)
        println()
    }

    @Test
    fun test003() {
        val metrics = listOf(
            "y.${BalanceSheet.资产合计.wholeKey}.t"
        )

        val metricsTotal = listOf(
            BalanceSheet.values().map { "y.${it.wholeKey}.t" },
            ProfitSheet.values().map { "y.${it.wholeKey}.t" },
            CashFlowSheet.values().map { "y.${it.wholeKey}.t" },
            Metrics.values().map { "y.${it.wholeKey}.t" }
        ).flatten()
            // .take(100)

        val r = Lixinger.post(
            url = LixingerUrls.FS_DATA,
            request = RequestFs(
                startDate = "2019-12-31",
                endDate = "2021-12-31",
                stockCodes = listOf("300750"),
                metricsList = metricsTotal
            ),
            responseClass = ResponseFs::class
        )
        println(r)
    }
}