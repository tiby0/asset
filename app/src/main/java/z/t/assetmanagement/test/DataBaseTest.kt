package z.t.assetmanagement.test

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.databasetest.*
import org.litepal.LitePal
import org.litepal.extension.deleteAll
import org.litepal.extension.find
import org.litepal.extension.updateAll
import z.t.assetmanagement.R
import z.t.assetmanagement.helpClass.ProcessUtil
import java.util.*

class DataBaseTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.databasetest)
//        var rightNow = Calendar.getInstance()
//        rightNow.time = Date()
//        rightNow.add(Calendar.DAY_OF_YEAR,-1)
//        var date = rightNow.time
        for (s in 10..15){
            var dataBaseTestBean = DataBaseTestBean()
            dataBaseTestBean.createdate = ProcessUtil.ConverToDate("2019-11-$s")
            dataBaseTestBean.amount = s.toDouble()
            dataBaseTestBean.save()
        }
    }

    fun creat(view: View) {
        var rightNow = Calendar.getInstance()
        rightNow.time = Date()
        rightNow.add(Calendar.DAY_OF_YEAR,-1)
        var date = rightNow.time.time
//        LitePal.findBySQL(" update DataBaseTestBean set amount = 55.0 ")
        LitePal.findBySQL("update DataBaseTestBean set createdate = date('now','-2 day'),amount = 55.0 ")
    }


    fun delete(view: View) {
        var rightNow = Calendar.getInstance()
        rightNow.time = Date()
        rightNow.add(Calendar.DAY_OF_YEAR,-1)
        var date = rightNow.time.time
//        LitePal.deleteAll<DataBaseTestBean>("createdate < ?",date.toString())
        LitePal.deleteAll<DataBaseTestBean>()
    }

    fun sqldelete(view: View) {
        var rightNow = Calendar.getInstance()
        rightNow.time = Date()
        rightNow.add(Calendar.DAY_OF_YEAR,-1)
        var date = rightNow.time.time
        LitePal.findBySQL("delete from DataBaseTestBean")
    }
}