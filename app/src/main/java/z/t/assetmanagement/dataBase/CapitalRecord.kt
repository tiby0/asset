package z.t.assetmanagement.dataBase

import org.litepal.crud.LitePalSupport

import java.util.Date


class CapitalRecord : LitePalSupport() {

//    lateinit var totalCapitalRecord:TotalCapitalRecord
    var unid: String = ""
    var createdate: Date? = null
    var versiondate: Date? = null
    var amount: Double = 0.0
    var status: String = ""
    var tpye: String = ""
    var tpyeName: String = ""
    var name: String = ""
    var level: String = ""
    var phase: Int = 0
    var change: Double = 0.0
    var remark: String = ""

    var twoType: String = ""

}
