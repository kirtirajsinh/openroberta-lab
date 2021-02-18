package de.fhg.iais.roberta.syntax.actors.raspberrypi;

import java.util.List;

import de.fhg.iais.roberta.blockly.generated.Block;
import de.fhg.iais.roberta.blockly.generated.Field;
import de.fhg.iais.roberta.blockly.generated.Value;
import de.fhg.iais.roberta.factory.BlocklyDropdownFactory;

import de.fhg.iais.roberta.inter.mode.action.IMotorMoveMode;

import de.fhg.iais.roberta.mode.action.MotorMoveMode;
import de.fhg.iais.roberta.mode.action.raspberrypi.DriveDirection;
import de.fhg.iais.roberta.syntax.*;
import de.fhg.iais.roberta.syntax.action.MoveAction;
import de.fhg.iais.roberta.syntax.lang.expr.Expr;
import de.fhg.iais.roberta.transformer.AbstractJaxb2Ast;
import de.fhg.iais.roberta.transformer.Ast2JaxbHelper;
import de.fhg.iais.roberta.transformer.ExprParam;
import de.fhg.iais.roberta.typecheck.BlocklyType;
import de.fhg.iais.roberta.util.dbc.Assert;
import de.fhg.iais.roberta.visitor.IVisitor;
import de.fhg.iais.roberta.visitor.hardware.IRaspberryPiVisitor;

/**
 * This class represents the <b>robActions_motor_on_for</b> and <b>robActions_motor_on</b> blocks from Blockly into the AST (abstract syntax tree). Object from
 * this class will generate code for setting the motor speed and type of movement connected on given port and turn the motor on.<br/>
 * <br/>
 * The client must provide the {@link String} and {@link MotionParam} (number of rotations or degrees and speed).
 */
public final class MotorRaspOnAction<V> extends MoveAction<V> {
    private final MotionParam<V> param;
    private final DriveDirection direction;

    private MotorRaspOnAction(String port, DriveDirection direction, MotionParam<V> param, BlocklyBlockProperties properties, BlocklyComment comment) {
        super(port, BlockTypeContainer.getByName("MOTOR_ON_ACTION_RASP"), properties, comment);
        Assert.isTrue((param != null) && (port != null));
        this.direction = direction;
        this.param = param;

        setReadOnly();
    }

    /**
     * Creates instance of {@link MotorRaspOnAction}. This instance is read only and can not be modified.
     *
     * @param port {@link String} on which the motor is connected,
     * @param param {@link MotionParam} that set up the parameters for the movement of the robot (number of rotations or degrees and speed),
     * @param properties of the block (see {@link BlocklyBlockProperties}),
     * @param comment added from the user,
     * @return read only object of class {@link MotorRaspOnAction}
     */
    public static <V> MotorRaspOnAction<V> make(
        String port,
        DriveDirection direction,
        MotionParam<V> param,
        BlocklyBlockProperties properties,
        BlocklyComment comment) {
        return new MotorRaspOnAction<>(port, direction, param, properties, comment);
    }

    /**
     * Transformation from JAXB object to corresponding AST object.
     *
     * @param block for transformation
     * @param helper class for making the transformation
     * @return corresponding AST object
     */
    public static <V> Phrase<V> jaxbToAst(Block block, AbstractJaxb2Ast<V> helper) {

        BlocklyDropdownFactory factory = helper.getDropdownFactory();
        List<Field> fields = helper.extractFields(block, (short) 2);
        String mode = helper.extractField(fields, BlocklyConstants.DIRECTION);
        String port = helper.extractField(fields, BlocklyConstants.MOTORPORT);
        List<Value> values;
        Phrase<V> power;
        MotionParam<V> mp;
        if ( block.getType().equals("robActions_motor_on_for_rasp") ||  block.getType().equals("robActions_robot_on_for_rasp")) {
            values = helper.extractValues(block, (short) 2);
            power = helper.extractValue(values, new ExprParam(BlocklyConstants.POWER, BlocklyType.NUMBER_INT));
            Phrase<V> duration = helper.extractValue(values, new ExprParam(BlocklyConstants.VALUE, BlocklyType.NUMBER_INT));
            MotorDuration<V> md = new MotorDuration<>(MotorMoveMode.DISTANCE, helper.convertPhraseToExpr(duration));
            mp = new MotionParam.Builder<V>().speed(helper.convertPhraseToExpr(power)).duration(md).build();
        } else {
            values = helper.extractValues(block, (short) 1);
            power = helper.extractValue(values, new ExprParam(BlocklyConstants.POWER, BlocklyType.NUMBER_INT));
            mp = new MotionParam.Builder<V>().speed(helper.convertPhraseToExpr(power)).build();
        }

        return MotorRaspOnAction.make(port, DriveDirection.get(mode), mp, helper.extractBlockProperties(block), helper.extractComment(block));
    }

    /**
     * @return {@link MotionParam} for the motor (number of rotations or degrees and speed).
     */
    public MotionParam<V> getParam() {
        return this.param;
    }

    /**
     * @return duration type of motor movement
     */
    public IMotorMoveMode getDurationMode() {
        return this.param.getDuration().getType();
    }

    /**
     * @return {@link DriveDirection} in which the robot will drive
     */
    public DriveDirection getDirection() {
        return this.direction;
    }

    /**
     * @return value of the duration of the motor movement
     */
    public Expr<V> getDurationValue() {
        MotorDuration<V> duration = this.param.getDuration();
        if ( duration != null ) {
            return duration.getValue();
        }
        return null;
    }

    @Override
    public String toString() {
        return "MotorOnAction [" + getUserDefinedPort() + ", " + this.param + "]";
    }

    @Override
    protected V acceptImpl(IVisitor<V> visitor) {
        return ((IRaspberryPiVisitor<V>) visitor).visitMotorRaspOnAction(this);
    }

    @Override
    public Block astToBlock() {
        Block jaxbDestination = new Block();
        Ast2JaxbHelper.setBasicProperties(this, jaxbDestination);

        Ast2JaxbHelper.addField(jaxbDestination, BlocklyConstants.DIRECTION, getDirection().toString());
        Ast2JaxbHelper.addField(jaxbDestination, BlocklyConstants.MOTORPORT, getUserDefinedPort());
        Ast2JaxbHelper.addValue(jaxbDestination, BlocklyConstants.POWER, getParam().getSpeed());
        String blockType = getProperty().getBlockType();
        if ( blockType.equals("robActions_motor_on_for_rasp") ||  blockType.equals("robActions_robot_on_for_rasp")) {
            Ast2JaxbHelper.addValue(jaxbDestination, BlocklyConstants.VALUE, getDurationValue());
        }
        return jaxbDestination;
    }
}
