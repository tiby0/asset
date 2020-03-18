package z.t.assetmanagement.dataBase

import org.litepal.crud.LitePalSupport
import java.util.*
import kotlin.collections.ArrayList
import org.litepal.LitePal
import org.litepal.extension.find


class TotalCapitalRecord : LitePalSupport() {

    var capitalRecordlist: List<CapitalRecord> = ArrayList()
    var createdate: Date? = null
    var versiondate: Date? = null
    var totalamount: Double = 0.0
    var name: String = ""
    var phase: Int = 0
    var totalchange: Double = 0.0
    var remark: String = ""

    fun get全部(): List<CapitalRecord> {
        val re = LitePal.where("phase=$phase").order("name,twotype").find<CapitalRecord>()
        for (a in re) {
            a.name = a.name + "    " + a.twoType
        }
        return re
//        return LitePal.where("phase=$phase").order("name").find()
    }

    fun get账户(): List<CapitalRecord> {
        val re = arrayListOf<CapitalRecord>()
        val cursor = LitePal.findBySQL(
            " select name,phase, sum(amount) sum " +
                    "from CapitalRecord where phase = $phase group by name,phase order by sum desc "
        )
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val a = CapitalRecord()
                a.name = cursor.getString(cursor.getColumnIndex("name")) ?: ""
                a.phase = (cursor.getString(cursor.getColumnIndex("phase")) ?: "0").toInt()
                a.amount = (cursor.getString(cursor.getColumnIndex("sum")) ?: "0").toDouble()
                re += a
            } while (cursor.moveToNext())

        }
        return re
//        return LitePal.where("phase=$phase").order("name").find()
    }

    fun get类型(): List<CapitalRecord> {

        val re = arrayListOf<CapitalRecord>()
        val cursor = LitePal.findBySQL(
            " select twotype,phase, sum(amount) sum " +
                    "from CapitalRecord where phase = $phase group by twotype,phase order by sum desc "
        )
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val a = CapitalRecord()
                a.name = cursor.getString(cursor.getColumnIndex("twotype")) ?: ""
                a.phase = (cursor.getString(cursor.getColumnIndex("phase")) ?: "0").toInt()
                a.amount = (cursor.getString(cursor.getColumnIndex("sum")) ?: "0").toDouble()
                re += a
            } while (cursor.moveToNext())

        }
        return re
//        return LitePal.where("phase=$phase").order("name").find()
    }
}