package com.moralabs.pet.core.utils

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputConnectionWrapper
import com.google.android.material.textfield.TextInputEditText

class CodeInputEditText : TextInputEditText {
    private var delKeyEventListener: OnDelKeyEventListener? = null

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection {
        return ZanyInputConnection(super.onCreateInputConnection(outAttrs), true, this)
    }

    /**
     * Override InputConnectionWrapper
     */
    private inner class ZanyInputConnection(target: InputConnection?, mutable: Boolean, var editText: CodeInputEditText) :
        InputConnectionWrapper(target, mutable) {
        /**
         * Re-sendKeyEnvent notifies that the external delete key is clicked by listening
         */
        override fun sendKeyEvent(event: KeyEvent): Boolean {
            if (event.action === KeyEvent.ACTION_DOWN && event.getKeyCode() === KeyEvent.KEYCODE_DEL) {
                if (delKeyEventListener != null) {
                    delKeyEventListener!!.onDeleteClick(editText)
                }
            }
            return super.sendKeyEvent(event)
        }

        /**
         * This method must be written. In some cases, the delete key will only call this method, and sendKeyEvent will not be called. You need to call it manually
         */
        override fun deleteSurroundingText(beforeLength: Int, afterLength: Int): Boolean {
            sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
            return super.deleteSurroundingText(beforeLength, afterLength)
        }
    }

    fun setDelKeyEventListener(delKeyEventListener: OnDelKeyEventListener?) {
        this.delKeyEventListener = delKeyEventListener
    }

    interface OnDelKeyEventListener {
        fun onDeleteClick(editText: CodeInputEditText?)
    }
}