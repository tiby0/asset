package z.t.assetmanagement.dataBase

import org.litepal.crud.LitePalSupport

import java.util.Date


class CapitalRecordType : LitePalSupport() {


    var unid: String = ""
    var createdate: Date? = null

    var name: String = ""

    var twoType: String = ""

}
