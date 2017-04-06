package net.ruigeng.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.ruigeng.myapplication.test.A;

public class MainActivity extends Activity {

	A a = new A();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		final TextView hello = (TextView) findViewById(R.id.tv_hello);
		hello.setText(a.getHello());

		Button button = (Button) findViewById(R.id.btn_update);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				hello.setText(a.getHello());
			}
		});
	}
}
