package com.stone.transition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.dkharrat.nexusdialog.FormController;
import com.github.dkharrat.nexusdialog.FormWithAppCompatActivity;
import com.github.dkharrat.nexusdialog.controllers.CheckBoxController;
import com.github.dkharrat.nexusdialog.controllers.DatePickerController;
import com.github.dkharrat.nexusdialog.controllers.EditTextController;
import com.github.dkharrat.nexusdialog.controllers.FormSectionController;
import com.github.dkharrat.nexusdialog.controllers.SelectionController;
import com.github.dkharrat.nexusdialog.controllers.TimePickerController;

import java.util.Arrays;

import cn.refactor.lib.colordialog.PromptDialog;

/**
 * Demonstrates the bare minimum to display a form in an Activity.
 */
public class InfoForm extends FormWithAppCompatActivity {
    private final static String CUSTOM_ELEM = "customElem";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override public void initForm(FormController formController) {
        setTitle("Donor Contact Info Form");
        Intent intent = getIntent();
        String childName = intent.getExtras().getString("CHILD_NAME");
        FormSectionController section = new FormSectionController(this, childName);
        section.addElement(new EditTextController(this, "firstName", "First name"));
        section.addElement(new EditTextController(this, "lastName", "Last name"));
        section.addElement(new EditTextController(this, "phoneNumber", "Phone Number"));
        formController.addSection(section);
        CustomElement customElem = new CustomElement(this, CUSTOM_ELEM, "Custom Element");
        customElem.getAddButton().setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                System.out.println("Clicked");
            }
        });
    }


    public void showPromptDialog(View view) {
        showPromptDlg();
    }

    private void showPromptDlg() {
        new PromptDialog(this)
                .setDialogType(PromptDialog.DIALOG_TYPE_WARNING)
                .setAnimationEnable(true)
                .setTitleText(getString(R.string.warning))
                .setContentText("Is this information correct?")
                .setPositiveListener(getString(R.string.ok), new PromptDialog.OnPositiveListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }
}