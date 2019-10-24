package com.wessam.chooseforme

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class MainActivityViewModel : ViewModel() {

    private var choice: MutableLiveData<String>? = null
    private var mUserInput: MutableLiveData<String>? = null
    private var choiceList: List<String>? = null
    private var imageVisibility: MutableLiveData<Boolean>? = MutableLiveData()
    private var resultVisibility: MutableLiveData<Boolean>? = MutableLiveData()

    fun setChoice(userInput: String) {
        //save user input
        if (mUserInput == null) {
            mUserInput = MutableLiveData()
        }
        mUserInput?.value = userInput

        //convert user input to list
        choiceList = userInput.split(".")
        val random = Random.nextInt(choiceList!!.size)
        if (choice == null) {
            choice = MutableLiveData()
        }
        choice?.value = choiceList!![random]
    }

    fun clear() {
        choiceList = null
        choice?.value = ""
        mUserInput?.value = ""
        imageVisibility?.value = true
        resultVisibility?.value = false
    }

    fun getChoice(): MutableLiveData<String>? {
        if (choice == null) {
            choice = MutableLiveData()
            choice?.value = ""
        }
        return choice
    }

    fun getUserInput(): MutableLiveData<String>? {
        if (mUserInput == null) {
            mUserInput = MutableLiveData()
            mUserInput?.value = ""
        }
        return mUserInput
    }

    fun getImageVisibility(): MutableLiveData<Boolean>? {
        return imageVisibility
    }

    fun getResultVisibility(): MutableLiveData<Boolean>? {
        return resultVisibility
    }

    fun setImageVisibility(b: Boolean) {
        imageVisibility?.value = b
    }

    fun setResultVisibility(b: Boolean) {
        resultVisibility?.value = b
    }
}