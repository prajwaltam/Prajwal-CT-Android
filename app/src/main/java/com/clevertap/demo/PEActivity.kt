package com.clevertap.demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.variables.Var
import com.clevertap.android.sdk.variables.callbacks.VariableCallback

class PEActivity : AppCompatActivity() {
    private var cleverTap: CleverTapAPI? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_peactivity)


        //All initialization
        cleverTap = CleverTapAPI.getDefaultInstance(applicationContext)
        CleverTapAPI.setDebugLevel(3)













        val var1: Var<Byte> = cleverTap!!.defineVariable("var_byte", 1)

        val var2: Var<Short> = cleverTap!!.defineVariable("var_short", 2)

        val var3: Var<Int> = cleverTap!!.defineVariable("var_int", 3)

        val var4: Var<Long> = cleverTap!!.defineVariable("var_long", 4L)

        val var5: Var<Float> = cleverTap!!.defineVariable("var_float", 5F)

        val var6: Var<Double> = cleverTap!!.defineVariable("var_double", 6.0)

        val var7: Var<String> = cleverTap!!.defineVariable("var_string", "str")

        val var8: Var<Boolean> = cleverTap!!.defineVariable("var_boolean", true)


// You must instruct CleverTap SDK that you have such definition by using:
        // You must instruct CleverTap SDK that you have such definition by using:
        cleverTap!!.parseVariablesForClasses(MyClass::class.java)

// You must instruct CleverTap SDK that you have such a definition by using:
        // You must instruct CleverTap SDK that you have such a definition by using:
        val instance = MyClass()
        cleverTap!!.parseVariables(instance)

        cleverTap!!.syncVariables();




        cleverTap!!.fetchVariables { isSuccess ->
            // isSuccess is true when server request is successful, false otherwise
            Log.d("ttt", "onCreate: fetchVariables =>$isSuccess")
        }



        var3.addValueChangedCallback(object : VariableCallback<Int>() {
            override fun onValueChanged(varInstance: Var<Int>) {
                // invoked on app start and whenever value is changed
                Log.d("ttt", "onCreate: onValueChanged =>${varInstance.stringValue}")

            }
        })






    }
}