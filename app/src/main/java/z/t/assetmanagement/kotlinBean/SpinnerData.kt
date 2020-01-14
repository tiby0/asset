package z.t.assetmanagement.kotlinBean

import android.widget.Spinner

class SpinnerData {

    var value = ""

    var text = ""

    var note = ""


    constructor() {
        value = ""
        text = ""
        note = ""
    }

    constructor(_value: String, _text: String) {
        value = _value
        text = _text
    }

    constructor(_value: String, _text: String, _note: String) {
        value = _value
        text = _text
        note = _note
    }

    override fun toString(): String {

        return text
    }
}