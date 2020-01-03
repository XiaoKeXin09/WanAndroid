package com.sqsong.wanandroid.di.module

import com.sqsong.wanandroid.di.scope.ActivityScope
import com.sqsong.wanandroid.ui.collection.CollectionActivity
import com.sqsong.wanandroid.ui.collection.di.CollectionModule
import com.sqsong.wanandroid.ui.home.activity.KnowledgeActivity
import com.sqsong.wanandroid.ui.home.activity.MainActivity
import com.sqsong.wanandroid.ui.home.di.ChildKnowledgeModule
import com.sqsong.wanandroid.ui.home.di.MainModule
import com.sqsong.wanandroid.ui.login.LoginActivity
import com.sqsong.wanandroid.ui.login.RegisterActivity
import com.sqsong.wanandroid.ui.login.di.LoginModule
import com.sqsong.wanandroid.ui.login.di.RegisterModule
import com.sqsong.wanandroid.ui.preview.ImagePreviewActivity
import com.sqsong.wanandroid.ui.preview.di.ImagePreviewModule
import com.sqsong.wanandroid.ui.scan.ScanningActivity
import com.sqsong.wanandroid.ui.search.SearchActivity
import com.sqsong.wanandroid.ui.search.di.SearchModule
import com.sqsong.wanandroid.ui.settings.SettingActivity
import com.sqsong.wanandroid.ui.settings.di.SettingModule
import com.sqsong.wanandroid.ui.splash.SplashActivity
import com.sqsong.wanandroid.ui.splash.di.SplashModule
import com.sqsong.wanandroid.ui.web.WebViewActivity
import com.sqsong.wanandroid.ui.wechat.PublicAccountActivity
import com.sqsong.wanandroid.ui.wechat.di.PublicAccountModule
import com.sqsong.wanandroid.ui.welfare.WelfareActivity
import com.sqsong.wanandroid.ui.welfare.di.WelfareModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [SplashModule::class])
    abstract fun contributeSplashActiivity(): SplashActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun contributeLoginActivity(): LoginActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [RegisterModule::class])
    abstract fun contributeRegisterActivity(): RegisterActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ChildKnowledgeModule::class])
    abstract fun contributeKnowledgeActivity(): KnowledgeActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [PublicAccountModule::class])
    abstract fun contributePublicAccountActivity(): PublicAccountActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeWebViewActivity(): WebViewActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [CollectionModule::class])
    abstract fun contributeCollectionActivity(): CollectionActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [SearchModule::class])
    abstract fun contributeSearchActivity(): SearchActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [SettingModule::class])
    abstract fun contributeSettingActivity(): SettingActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [WelfareModule::class])
    abstract fun contributeWelfareActivity(): WelfareActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ImagePreviewModule::class])
    abstract fun contributeImagePreviewActivity(): ImagePreviewActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeScanningActivity(): ScanningActivity
}