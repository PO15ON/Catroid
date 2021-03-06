/*
* Catroid: An on-device visual programming system for Android devices
* Copyright (C) 2010-2018 The Catrobat Team
* (<http://developer.catrobat.org/credits>)
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Affero General Public License as
* published by the Free Software Foundation, either version 3 of the
* License, or (at your option) any later version.
*
* An additional term exception under section 7 of the GNU Affero
* General Public License, version 3, is available at
* http://developer.catrobat.org/license_additional_term
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Affero General Public License for more details.
*
* You should have received a copy of the GNU Affero General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.catrobat.catroid.uiespresso.ui.regression.activitydestroy;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;

import org.catrobat.catroid.R;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.bricks.ChangeSizeByNBrick;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTSensor;
import org.catrobat.catroid.ui.SpriteActivity;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.catrobat.catroid.uiespresso.annotations.Flaky;
import org.catrobat.catroid.uiespresso.content.brick.utils.BrickTestUtils;
import org.catrobat.catroid.uiespresso.formulaeditor.utils.FormulaEditorWrapper;
import org.catrobat.catroid.uiespresso.testsuites.Cat;
import org.catrobat.catroid.uiespresso.testsuites.Level;
import org.catrobat.catroid.uiespresso.util.rules.BaseActivityInstrumentationRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static org.catrobat.catroid.uiespresso.content.brick.utils.BrickDataInteractionWrapper.onBrickAtPosition;
import static org.catrobat.catroid.uiespresso.formulaeditor.utils.FormulaEditorWrapper.onFormulaEditor;

@Category({Cat.AppUi.class, Level.Smoke.class})
public class LegoConfigDialogActivityRecreationRegressionTest {

	@Rule
	public BaseActivityInstrumentationRule<SpriteActivity> baseActivityTestRule = new
					BaseActivityInstrumentationRule<>(SpriteActivity.class, SpriteActivity.EXTRA_FRAGMENT_POSITION, SpriteActivity.FRAGMENT_SCRIPTS);

	private NXTSensor.Sensor[] sensorMapping;
	private boolean legoNXTBrickSetting;

	@Before
	public void setUp() throws Exception {
		Script script = BrickTestUtils.createProjectAndGetStartScript("LegoSensorPortConfigDialogTest");
		script.addBrick(new ChangeSizeByNBrick(0));

		legoNXTBrickSetting = getNXTBrickSetting();
		setNXTBrickSetting(true);

		sensorMapping = SettingsFragment.getLegoNXTSensorMapping(InstrumentationRegistry.getTargetContext());

		SettingsFragment.setLegoMindstormsNXTSensorMapping(InstrumentationRegistry.getTargetContext(),
						new NXTSensor.Sensor[] {NXTSensor.Sensor.NO_SENSOR, NXTSensor.Sensor.NO_SENSOR,
								NXTSensor.Sensor.NO_SENSOR, NXTSensor.Sensor.NO_SENSOR});

		baseActivityTestRule.launchActivity();
	}

	@Category({Cat.AppUi.class, Level.Smoke.class, Cat.Quarantine.class})
	@Flaky
	@Test
	public void testActivityRecreateLegoConfigDialog() {
		onBrickAtPosition(1).onChildView(withId(R.id.brick_change_size_by_edit_text)).perform(click());

		onFormulaEditor()
				.performOpenCategory(FormulaEditorWrapper.Category.DEVICE)
				.performSelect(R.string.formula_editor_sensor_lego_nxt_touch);

		InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
			@Override
			public void run() {
				baseActivityTestRule.getActivity().recreate();
			}
		});

		InstrumentationRegistry.getInstrumentation().waitForIdleSync();
	}

	@After
	public void tearDown() {
		SettingsFragment.setLegoMindstormsNXTSensorMapping(InstrumentationRegistry.getTargetContext(), sensorMapping);
		setNXTBrickSetting(legoNXTBrickSetting);
	}

	private void setNXTBrickSetting(boolean bricksEnabled) {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(InstrumentationRegistry.getTargetContext()).edit();
		editor.putBoolean(SettingsFragment.SETTINGS_MINDSTORMS_NXT_BRICKS_ENABLED, bricksEnabled).commit();
	}

	private boolean getNXTBrickSetting() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(InstrumentationRegistry.getTargetContext());
		return sharedPreferences.getBoolean(SettingsFragment.SETTINGS_MINDSTORMS_NXT_BRICKS_ENABLED, false);
	}
}
