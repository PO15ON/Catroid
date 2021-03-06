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
package org.catrobat.catroid.test.content.bricks;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.SingleSprite;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.StartScript;
import org.catrobat.catroid.content.WhenConditionScript;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.ChangeBrightnessByNBrick;
import org.catrobat.catroid.content.bricks.ChangeColorByNBrick;
import org.catrobat.catroid.content.bricks.ChangeSizeByNBrick;
import org.catrobat.catroid.content.bricks.ChangeTransparencyByNBrick;
import org.catrobat.catroid.content.bricks.ChangeVariableBrick;
import org.catrobat.catroid.content.bricks.ChangeVolumeByNBrick;
import org.catrobat.catroid.content.bricks.ChangeXByNBrick;
import org.catrobat.catroid.content.bricks.ChangeYByNBrick;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.content.bricks.GlideToBrick;
import org.catrobat.catroid.content.bricks.GoNStepsBackBrick;
import org.catrobat.catroid.content.bricks.IfLogicBeginBrick;
import org.catrobat.catroid.content.bricks.LegoNxtMotorMoveBrick;
import org.catrobat.catroid.content.bricks.LegoNxtMotorTurnAngleBrick;
import org.catrobat.catroid.content.bricks.LegoNxtPlayToneBrick;
import org.catrobat.catroid.content.bricks.MoveNStepsBrick;
import org.catrobat.catroid.content.bricks.NoteBrick;
import org.catrobat.catroid.content.bricks.PlaceAtBrick;
import org.catrobat.catroid.content.bricks.RepeatBrick;
import org.catrobat.catroid.content.bricks.SetBrightnessBrick;
import org.catrobat.catroid.content.bricks.SetColorBrick;
import org.catrobat.catroid.content.bricks.SetSizeToBrick;
import org.catrobat.catroid.content.bricks.SetTransparencyBrick;
import org.catrobat.catroid.content.bricks.SetVariableBrick;
import org.catrobat.catroid.content.bricks.SetVolumeToBrick;
import org.catrobat.catroid.content.bricks.SetXBrick;
import org.catrobat.catroid.content.bricks.SetYBrick;
import org.catrobat.catroid.content.bricks.SpeakBrick;
import org.catrobat.catroid.content.bricks.TurnLeftBrick;
import org.catrobat.catroid.content.bricks.TurnRightBrick;
import org.catrobat.catroid.content.bricks.UserVariableBrick;
import org.catrobat.catroid.content.bricks.VibrationBrick;
import org.catrobat.catroid.content.bricks.WaitBrick;
import org.catrobat.catroid.content.bricks.WhenConditionBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.FormulaElement.ElementType;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.test.utils.TestUtils;
import org.catrobat.catroid.ui.recyclerview.controller.SpriteController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Constructor;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertSame;

@RunWith(AndroidJUnit4.class)
public class BrickCloneTest {

	private static final int BRICK_FORMULA_VALUE = 1;
	private static final String CLONE_BRICK_FORMULA_VALUE = "2";
	private static final String VARIABLE_NAME = "test_variable";
	private Sprite sprite;

	@Before
	public void setUp() throws Exception {
		sprite = new SingleSprite("testSprite");
	}

	@Test
	public void testBrickCloneWithFormula() throws CloneNotSupportedException, InterpretationException {
		Brick brick = new ChangeBrightnessByNBrick(new Formula(BRICK_FORMULA_VALUE));
		brickClone(brick, Brick.BrickField.BRIGHTNESS_CHANGE);

		brick = new ChangeTransparencyByNBrick(new Formula(BRICK_FORMULA_VALUE));
		brickClone(brick, Brick.BrickField.TRANSPARENCY_CHANGE);

		brick = new ChangeSizeByNBrick(new Formula(BRICK_FORMULA_VALUE));
		brickClone(brick, Brick.BrickField.SIZE_CHANGE);

		brick = new ChangeVariableBrick(BRICK_FORMULA_VALUE);
		brickClone(brick, Brick.BrickField.VARIABLE_CHANGE);

		brick = new ChangeVolumeByNBrick(BRICK_FORMULA_VALUE);
		brickClone(brick, Brick.BrickField.VOLUME_CHANGE);

		brick = new ChangeXByNBrick(BRICK_FORMULA_VALUE);
		brickClone(brick, Brick.BrickField.X_POSITION_CHANGE);

		brick = new ChangeYByNBrick(BRICK_FORMULA_VALUE);
		brickClone(brick, Brick.BrickField.Y_POSITION_CHANGE);

		brick = new GoNStepsBackBrick(BRICK_FORMULA_VALUE);
		brickClone(brick, Brick.BrickField.STEPS);

		brick = new IfLogicBeginBrick(10);
		brickClone(brick, Brick.BrickField.IF_CONDITION);

		brick = new LegoNxtMotorMoveBrick(LegoNxtMotorMoveBrick.Motor.MOTOR_A, BRICK_FORMULA_VALUE);
		brickClone(brick, Brick.BrickField.LEGO_NXT_SPEED);

		brick = new LegoNxtMotorTurnAngleBrick(LegoNxtMotorTurnAngleBrick.Motor.MOTOR_A, BRICK_FORMULA_VALUE);
		brickClone(brick, Brick.BrickField.LEGO_NXT_DEGREES);

		brick = new LegoNxtPlayToneBrick(BRICK_FORMULA_VALUE, BRICK_FORMULA_VALUE);
		brickClone(brick, Brick.BrickField.LEGO_NXT_FREQUENCY, Brick.BrickField.LEGO_NXT_DURATION_IN_SECONDS);

		brick = new MoveNStepsBrick(BRICK_FORMULA_VALUE);
		brickClone(brick, Brick.BrickField.STEPS);

		brick = new RepeatBrick(BRICK_FORMULA_VALUE);
		brickClone(brick, Brick.BrickField.TIMES_TO_REPEAT);

		brick = new SetBrightnessBrick(BRICK_FORMULA_VALUE);
		brickClone(brick, Brick.BrickField.BRIGHTNESS);

		brick = new SetTransparencyBrick(BRICK_FORMULA_VALUE);
		brickClone(brick, Brick.BrickField.TRANSPARENCY);

		brick = new SetColorBrick((float) BRICK_FORMULA_VALUE);
		brickClone(brick, Brick.BrickField.COLOR);

		brick = new ChangeColorByNBrick((float) BRICK_FORMULA_VALUE);
		brickClone(brick, Brick.BrickField.COLOR_CHANGE);

		brick = new SetSizeToBrick(BRICK_FORMULA_VALUE);
		brickClone(brick, Brick.BrickField.SIZE);

		brick = new SetVariableBrick(BRICK_FORMULA_VALUE);
		brickClone(brick, Brick.BrickField.VARIABLE);

		brick = new SetVolumeToBrick(BRICK_FORMULA_VALUE);
		brickClone(brick, Brick.BrickField.VOLUME);

		brick = new SetXBrick(BRICK_FORMULA_VALUE);
		brickClone(brick, Brick.BrickField.X_POSITION);

		brick = new SetYBrick(BRICK_FORMULA_VALUE);
		brickClone(brick, Brick.BrickField.Y_POSITION);

		brick = new TurnLeftBrick(BRICK_FORMULA_VALUE);
		brickClone(brick, Brick.BrickField.TURN_LEFT_DEGREES);

		brick = new TurnRightBrick(BRICK_FORMULA_VALUE);
		brickClone(brick, Brick.BrickField.TURN_RIGHT_DEGREES);

		brick = new VibrationBrick(BRICK_FORMULA_VALUE);
		brickClone(brick, Brick.BrickField.VIBRATE_DURATION_IN_SECONDS);

		brick = new WaitBrick(BRICK_FORMULA_VALUE);
		brickClone(brick, Brick.BrickField.TIME_TO_WAIT_IN_SECONDS);

		brick = new PlaceAtBrick(BRICK_FORMULA_VALUE, BRICK_FORMULA_VALUE);
		brickClone(brick, Brick.BrickField.X_POSITION, Brick.BrickField.Y_POSITION);

		brick = new GlideToBrick(BRICK_FORMULA_VALUE, BRICK_FORMULA_VALUE, BRICK_FORMULA_VALUE);
		brickClone(brick, Brick.BrickField.X_DESTINATION, Brick.BrickField.Y_DESTINATION,
				Brick.BrickField.DURATION_IN_SECONDS);

		brick = new NoteBrick(String.valueOf(BRICK_FORMULA_VALUE));
		brickClone(brick, Brick.BrickField.NOTE);

		brick = new SpeakBrick(String.valueOf(BRICK_FORMULA_VALUE));
		brickClone(brick, Brick.BrickField.SPEAK);
	}

	@Test
	public void testWhenConditionBrickFormulaMapSameAsWhenConditionScriptFormulaMap() throws CloneNotSupportedException {
		WhenConditionBrick brickClone =
				(WhenConditionBrick) new WhenConditionBrick(new WhenConditionScript(new Formula(0))).clone();
		assertSame(brickClone.getFormulaMap(), ((WhenConditionScript) brickClone.getScript()).getFormulaMap());
	}

	@Test
	public void testVariableReferencesChangeVariableBrick() throws Exception {
		checkVariableReferences(ChangeVariableBrick.class);
	}

	@Test
	public void testVariableReferencesSetVariableBrick() throws Exception {
		checkVariableReferences(SetVariableBrick.class);
	}

	private <T extends Brick> void checkVariableReferences(Class<T> typeOfBrick) throws Exception {
		Project project = new Project(InstrumentationRegistry.getTargetContext(), TestUtils.DEFAULT_TEST_PROJECT_NAME);
		ProjectManager.getInstance().setCurrentProject(project);
		project.getDefaultScene().addSprite(sprite);
		StartScript script = new StartScript();
		sprite.addScript(script);
		project.getDefaultScene().getDataContainer().addUserVariable(sprite, new UserVariable(VARIABLE_NAME));
		UserVariable spriteVariable = project.getDefaultScene().getDataContainer().getUserVariable(sprite, VARIABLE_NAME);
		Formula formula = new Formula(new FormulaElement(ElementType.USER_VARIABLE, VARIABLE_NAME, null));

		// create brick - expects:
		// public SetVariableBrick(Formula variableFormula, UserVariable userVariable)
		Constructor<T> constructor = typeOfBrick.getDeclaredConstructor(Formula.class, UserVariable.class);
		T toBeTestedBrick = constructor.newInstance(formula, spriteVariable);

		script.addBrick(toBeTestedBrick);

		Sprite clonedSprite = new SpriteController().copy(sprite,
				project.getDefaultScene(),
				project.getDefaultScene());

		@SuppressWarnings("unchecked")
		T clonedBrick = (T) clonedSprite.getScript(0).getBrick(0);
		UserVariable clonedVariable = project.getDefaultScene().getDataContainer().getUserVariable(clonedSprite, VARIABLE_NAME);
		UserVariable clonedVariableFromBrick = ((UserVariableBrick) clonedBrick).getUserVariable();

		assertNotNull(clonedVariable);
		assertNotSame(spriteVariable, clonedVariable);
		assertNotSame(spriteVariable, clonedVariableFromBrick);
		assertEquals(clonedVariable, clonedVariableFromBrick);
	}

	private void brickClone(Brick brick, Brick.BrickField... brickFields) throws CloneNotSupportedException, InterpretationException {
		Brick cloneBrick = brick.clone();
		for (Brick.BrickField brickField : brickFields) {
			Formula brickFormula = ((FormulaBrick) brick).getFormulaWithBrickField(brickField);
			Formula cloneBrickFormula = ((FormulaBrick) cloneBrick).getFormulaWithBrickField(brickField);
			cloneBrickFormula.setRoot(new FormulaElement(ElementType.NUMBER, CLONE_BRICK_FORMULA_VALUE, null));
			assertNotSame(brickFormula.interpretInteger(sprite), cloneBrickFormula.interpretInteger(sprite));
		}
	}
}

