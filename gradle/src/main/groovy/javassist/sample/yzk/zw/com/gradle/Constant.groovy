package javassist.sample.yzk.zw.com.gradle

class Constant {
    static final def FragmentV4 = "android/support/v4/app/Fragment"
    static final def OnClickListener = "android/view/View\$OnClickListener"
    static final def ClickStatistics = "com/zw/yzk/component/statistics/statistics/ClickStatistics"
    static final def FragmentStatistics = "com/zw/yzk/component/statistics/statistics/FragmentStatistics"
    static final def FragmentStatisticsType = "Lcom/zw/yzk/component/statistics/statistics/FragmentStatistics;"
    static final def FragmentParamDesc = "(Landroid/support/v4/app/Fragment;)V"
    //用于统计fragment页面信息
    static final def statisticsField = "fragmentStatistics"

    //构造函数的函数名
    static final def constructor = "<init>"

    //生命周期函数onCreateView
    static final def onCreateView = "onCreateView"
    static final def onCreateViewDesc = "(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;"

    //生命周期函数onHiddenChanged
    static final def onHiddenChanged = "onHiddenChanged"
    static final def onHiddenChangedDesc = "(Z)V"

    //生命周期函数onResume
    static final def onResume = "onResume"
    static final def onResumeDesc = "()V"

    //生命周期函数onPause
    static final def onPause = "onPause"
    static final def onPauseDesc = "()V"

    //生命周期函数setUserVisibleHint
    static final def setUserVisibleHint = "setUserVisibleHint"
    static final def setUserVisibleHintDesc = "(Z)V"

    //生命周期函数onDestroyView
    static final def onDestroyView = "onDestroyView"
    static final def onDestroyViewDesc = "()V"

    //onClick
    static final def onClick = "onClick"
    static final def clickEnd = "clickEnd"
    static final def clickEndDesc = "()V"

    //ClickStatistics构造函数描述
    static final def ClickStatisticsDesc = "(Landroid/view/View;)V"

}