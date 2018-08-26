package javassist.sample.yzk.zw.com.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 对点击事件进行修改，这里是所有的点击事件
 *
 * onClick(Landroid/view/View;)V
 * onClick(Landroid/content/DialogInterface;I)V
 * onItemClick(Landroid/widget/AdapterView;Landroid/view/View;IJ)V
 * onItemSelected(Landroid/widget/AdapterView;Landroid/view/View;IJ)V
 * onGroupClick(Landroid/widget/ExpandableListView;Landroid/view/View;IJ)Z
 * onChildClick(Landroid/widget/ExpandableListView;Landroid/view/View;IIJ)Z
 * onRatingChanged(Landroid/widget/RatingBar;FZ)V
 * onStopTrackingTouch(Landroid/widget/SeekBar;)V
 * onCheckedChanged(Landroid/widget/CompoundButton;Z)V
 * onCheckedChanged(Landroid/widget/RadioGroup;I)V
 */
class ModifyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println "this is my custom plugin ModifyPlugin"

        project.android.registerTransform(new ModifyTransform())
    }

}