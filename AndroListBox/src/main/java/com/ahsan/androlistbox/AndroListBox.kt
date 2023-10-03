package com.ahsan.androlistbox

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.iterator
import com.google.android.material.chip.ChipGroup


class AndroListBox(context1: Context, attrs: AttributeSet?) :
    LinearLayout(context1, attrs) {
    private var adapter: ArrayAdapter<String>
    private var sugList: ArrayList<String>
    private var filterdList: ArrayList<String>
    private var languageInput: AutoCompleteTextView


    init {
        val view = LayoutInflater.from(context1)
            .inflate(R.layout.material_entry_chips_input, this, true)

        languageInput = view.findViewById<AutoCompleteTextView>(R.id.languages_input)
        val languagesLayout = view.findViewById<LinearLayout>(R.id.languages_layout)
        val myChipGroup = view.findViewById<ChipGroup>(R.id.chip_group)

        sugList = ArrayList()
        filterdList = ArrayList(sugList)
        adapter =
            ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, filterdList)
        languageInput.setAdapter(adapter)
        languageInput.setOnItemClickListener { adapterView, view, i, l ->
            val materialChip = com.google.android.material.chip.Chip(context)
            materialChip.text = adapterView.getItemAtPosition(i) as String
            materialChip.setEnsureMinTouchTargetSize(false)
//            materialChip.ensureAccessibleTouchTarget(24)
            materialChip.isCloseIconVisible = true
            materialChip.chipBackgroundColor =
                ContextCompat.getColorStateList(context, R.color.blue)
            materialChip.closeIconTint =
                ContextCompat.getColorStateList(context, R.color.white)
            materialChip.setTextColor(ContextCompat.getColor(context, R.color.white))
            materialChip.setOnCloseIconClickListener {
                filterdList.add(materialChip.text as String)
                myChipGroup.removeView(materialChip)
                adapter =
                    ArrayAdapter(
                        context,
                        android.R.layout.simple_dropdown_item_1line,
                        filterdList
                    )
                languageInput.setAdapter(adapter)

            }
            myChipGroup.addView(materialChip, myChipGroup.indexOfChild(languageInput))
            languageInput.setText("")
            filterdList.clear()
            filterdList = ArrayList(sugList)
            for (item in myChipGroup) {
                if (item is com.google.android.material.chip.Chip) {
                    filterdList.remove(item.text.toString())
                }
            }
            adapter =
                ArrayAdapter(
                    context,
                    android.R.layout.simple_dropdown_item_1line,
                    filterdList
                )
            languageInput.setAdapter(adapter)

        }

        languageInput.setOnClickListener(View.OnClickListener { languageInput.showDropDown() })

        languagesLayout.setOnClickListener {
            languageInput.requestFocus()
            languageInput.showDropDown()
        }
    }

    fun setSuggestionsList(list: ArrayList<String>) {
        this.sugList = ArrayList(list)
        this.filterdList = ArrayList(sugList)
        this.adapter =
            ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, filterdList)
        this.languageInput.setAdapter(adapter)
    }
}