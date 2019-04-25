// TODO: Adaptive text size for TextViews.
package io.github.murenkov.calculatorforandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalculatorActivity extends AppCompatActivity {

    private TextView mInputStatement;
    private TextView mOutputStatement;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        Button mBackspaceButton;

        mInputStatement = (TextView) findViewById(R.id.input_statement);
        //TODO: Change deprecated method to new.
        mInputStatement.setTextColor(getResources().getColor(R.color.colorAccent));
        mOutputStatement = (TextView) findViewById(R.id.output_statement);

        mBackspaceButton = (Button) findViewById(R.id.backspace_button);
        mBackspaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence inputStatement = mInputStatement.getText();
                if (!inputStatement.toString().isEmpty()) {
                    if (inputStatement.length() < 2) {
                        clearScreen();
                    } else {
                        mInputStatement.setText(inputStatement.subSequence(0, inputStatement.length() - 1));
                        updateOutputStatement();
                    }
                }
            }
        });
        mBackspaceButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clearScreen();
                return false;
            }
        });
    }

    private void clearScreen() {
        mInputStatement.setText("");
        mOutputStatement.setText("");
    }

    private void updateOutputStatement() {
        int errorMessage = R.string.default_error_message;
        try {
            //TODO: Change deprecated method to new.
            mOutputStatement.setTextColor(getResources().getColor(R.color.colorAccent));
            if (Calculator.areBracketsBalanced(mInputStatement.getText().toString())) {
                mOutputStatement.setText(String.valueOf(Calculator.calculatePostfix(
                        Calculator.convertToPostfix(mInputStatement.getText().toString()))));
            } else {
                errorMessage = R.string.unbalanced_brackets_message;
                throw new Exception("Unbalanced braces!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //TODO: Change deprecated method to new.
            mOutputStatement.setTextColor(getResources().getColor(R.color.colorError));
            mOutputStatement.setText(errorMessage);
        }
    }

    public void onWritingClick(View view) {
        Button button = (Button) view;
        mInputStatement.append(button.getText());
        updateOutputStatement();
    }
}
