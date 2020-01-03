package com.sqsong.wanandroid.ui.home.mvp.home

import com.sqsong.wanandroid.data.BaseData
import com.sqsong.wanandroid.data.HomeBannerBean
import com.sqsong.wanandroid.data.HomeItemBean
import com.sqsong.wanandroid.network.ApiService
import io.reactivex.Observable
import javax.inject.Inject

class HomeModel @Inject constructor(private val apiService: ApiService) : HomeContract.Model {

    override fun getHomeDataList(page: Int): Observable<HomeItemBean> = apiService.getHomeDataList(page)

    override fun getBannerData(): Observable<HomeBannerBean> = apiService.getHomeBanner()

    override fun collectArticle(articleId: Int): Observable<BaseData> = apiService.collectArticle(articleId)

    override fun unCollectArticle(articleId: Int): Observable<BaseData> = apiService.unCollectArticle(articleId)

    override fun onDestroy() {
    }

}