package z.t.assetmanagement.test

import org.litepal.crud.LitePalSupport

import java.util.Date


class DataBaseTestBean : LitePalSupport() {

    var unid: String = ""
    var createdate: Date? = null
    var versiondate: Date? = null
    var amount: Double = 0.0
    var status: String = ""
    var tpye: String = ""
    var tpyeName: String = ""
    var name: String = ""
    var level: String = ""
    var phase: String = ""
    var change: String = ""
    var remark: String = ""

}
