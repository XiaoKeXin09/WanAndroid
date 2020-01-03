package com.sqsong.wanandroid.ui.login.mvp

import android.widget.TextView
import com.sqsong.wanandroid.data.BaseBean
import com.sqsong.wanandroid.data.BaseData
import com.sqsong.wanandroid.mvp.IModel
import com.sqsong.wanandroid.mvp.IView
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

interface RegisterContract {

    interface View : IView {
        fun getTitleText(): TextView
        fun backDisposable(): Disposable
        fun userNameDisposable(): Disposable
        fun passwordDisposable(): Disposable
        fun confirmPasswordDisposable(): Disposable
        fun commitObservable(): Observable<Any>
        fun userNameText(): String
        fun passwordText(): String
        fun confirmPasswordText(): String
        /**
         * @param type 0 - name input layout type; 1 - password input layout type; 2 - password length type;
         * 3 - confirm password input layout type;
         * @param errorMsg error message tips.
         */
        fun showInputLayoutError(type: Int, errorMsg: String)
        fun showProcessDialog()
        fun hideProcessDialog()
        fun finishActivity()
    }

    interface Model : IModel {
        fun register(userName: String, password: String, rePassword: String): Observable<BaseData>
    }
}