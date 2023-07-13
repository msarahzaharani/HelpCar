package com.example.helpcarapps;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

public class InstructionsDialog extends Dialog {

    private int instructionPoints = 0;
    public InstructionsDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructions_dialog_layout);

        final AppCompatButton continueBtn = findViewById(R.id.continuebtn);
        final TextView instructionsTv= findViewById(R.id.instructionsTv);

        setInstructionPoint(instructionsTv,"1. You will get maximum 2 minutes to complete the quiz.");
        setInstructionPoint(instructionsTv, "2. You will get 1 point on every correct answer");

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void setInstructionPoint(TextView instructionsTv, String instructionPoint){

        if (instructionPoints == 0){
            instructionsTv.setText(instructionPoint);

        }
        else{
            instructionsTv.setText(instructionsTv.getText()+"\n\n"+instructionPoint);
        }
    }
}
