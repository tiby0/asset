package z.t.assetmanagement.dataBase

import org.litepal.crud.LitePalSupport
import java.util.*
import kotlin.collections.ArrayList
import org.litepal.LitePal
import org.litepal.extension.find


class TotalCapitalRecord : LitePalSupport() {

    var capitalRecordlist:List<CapitalRecord> = ArrayList()
    var createdate: Date? = null
    var versiondate: Date? = null
    var totalamount: Double = 0.0
    var name: String = ""
    var phase: Int = 0
    var totalchange: Double = 0.0
    var remark: String = ""

    fun getList():List<CapitalRecord>{

        return LitePal.where("phase=$phase").find()
//        return LitePal.where("phase=$phase").order("name").find()
    }
}