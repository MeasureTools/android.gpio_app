/**
 * Copyright 2016 Daniel "Dadie" Korner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * distributed under the License is distributed on an "AS IS" BASIS,
 * Unless required by applicable law or agreed to in writing, software
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package info.eismango.gpio;

import android.net.Uri;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import info.eismango.grimgal.*;

public class GPIOActivity extends Activity {
	
	List<Button> buttons = new ArrayList<Button>();
	ScrollView   scroll;
	LinearLayout layout;
	LayoutParams layoutParams;
	
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		
		
		// ###### Layout [BEGIN] ######
		
		this.layout = new LinearLayout(this);
		{
			this.layout.setOrientation(LinearLayout.VERTICAL);
		}
		this.scroll = new ScrollView(this);
		{
			this.scroll.addView(this.layout);
		}
		this.setContentView(this.scroll);
		
		int[] gpios = {33, 23, 29, 30, 22, 18, 21, 190, 191, 192, 174, 173, 171, 172, 189, 210, 209, 19, 28, 31, 25, 24};
		Arrays.sort(gpios);
		for (final int gpio : gpios) {
			final Button button = new Button(this);
			{
				button.setHeight(20);
				button.setWidth(200);
				//button.setTag(this.buttons.size());
				String direction;
				if (GPIO.create(gpio).isIn()) {
					direction = "in";
				}
				else {
					direction = "out";
				}
				String value     = "" + GPIO.create(gpio).getValue();
				button.setText("GPIO " + gpio + " D("+direction+") V("+value+")");
				
				OnClickListener buttonClicked = new OnClickListener() {
					@Override
					public void onClick(View v) {
						GPIO g = GPIO.create(gpio);
						if (g.isIn()) {
							g.setOut();
						}
						if (!g.getValue()) {
							g.setValue(true);
						}
						else {
							g.setValue(false);
						}
						String direction;
						if (GPIO.create(gpio).isIn()) {
							direction = "in";
						}
						else {
							direction = "out";
						}
						String value     = "" + GPIO.create(gpio).getValue();
						button.setText("GPIO " + gpio + " D("+direction+") V("+value+")");
					}
				};
 				button.setOnClickListener(buttonClicked);
			}
			this.layout.addView(button);
			this.buttons.add(button);
		}
		// ###### Layout [END] ######
		
	}
	
}
