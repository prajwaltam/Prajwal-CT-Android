package com.clevertap.demo

import com.clevertap.android.sdk.inapp.customtemplates.CustomTemplateContext
import com.clevertap.android.sdk.inapp.customtemplates.TemplatePresenter
import kotlin.math.log

class MyTemplatePresenter: TemplatePresenter {

    override fun onPresent(context: CustomTemplateContext.TemplateContext) {
        // be sure to keep the context as long as the template UI is being displayed
        // so that context.setDismissed() can be called when the UI is closed.
        showUI(context)
        context.setPresented()
    }

    private fun showUI(context: CustomTemplateContext.TemplateContext) {

  //log here data is logic to be handled
    }

    override fun onClose(context: CustomTemplateContext.TemplateContext) {
        // close the corresponding UI
        context.setDismissed()
    }
}
