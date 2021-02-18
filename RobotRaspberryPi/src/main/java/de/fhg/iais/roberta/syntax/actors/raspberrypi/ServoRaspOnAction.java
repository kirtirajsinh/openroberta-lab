package de.fhg.iais.roberta.syntax.actors.raspberrypi;

import java.util.List;

import de.fhg.iais.roberta.blockly.generated.Block;
import de.fhg.iais.roberta.blockly.generated.Field;
import de.fhg.iais.roberta.blockly.generated.Value;
import de.fhg.iais.roberta.factory.BlocklyDropdownFactory;
import de.fhg.iais.roberta.inter.mode.action.IMotorServoMode;
import de.fhg.iais.roberta.mode.action.MotorMoveMode;
import de.fhg.iais.roberta.syntax.*;
import de.fhg.iais.roberta.syntax.action.MoveAction;
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
public final class ServoRaspOnAction<V> extends MoveAction<V> {
    private final IMotorServoMode mode;

    private ServoRaspOnAction(String port, IMotorServoMode mode, BlocklyBlockProperties properties, BlocklyComment comment) {
        super(port, BlockTypeContainer.getByName("MOTOR_SERVO_ACTION_RASP"), properties, comment);
        Assert.isTrue((mode != null) && (port != null));
        this.mode = mode;
        setReadOnly();
    }

    /**
     * Creates instance of {@link ServoRaspOnAction}. This instance is read only and can not be modified.
     *
     * @param port {@link String} on which the motor is connected,
     * @param mode {@link MotionParam} that set up the parameters for the movement of the robot (number of rotations or degrees and speed),
     * @param properties of the block (see {@link BlocklyBlockProperties}),
     * @param comment added from the user,
     * @return read only object of class {@link ServoRaspOnAction}
     */
    public static <V> ServoRaspOnAction<V> make(String port, IMotorServoMode mode, BlocklyBlockProperties properties, BlocklyComment comment) {
        return new ServoRaspOnAction<>(port, mode, properties, comment);
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
        String mode = helper.extractField(fields, BlocklyConstants.POSITION);
        String port = helper.extractField(fields, BlocklyConstants.MOTORPORT);

        return ServoRaspOnAction.make(port, factory.getMotorServoMode(mode), helper.extractBlockProperties(block), helper.extractComment(block));
    }

    public IMotorServoMode getMode() {
        return mode;
    }

    @Override
    public String toString() {
        return "MotorOnAction [" + getUserDefinedPort() + ", " + this.mode + "]";
    }

    @Override
    protected V acceptImpl(IVisitor<V> visitor) {
        return ((IRaspberryPiVisitor<V>) visitor).visitServoRaspOnAction(this);
    }

    @Override
    public Block astToBlock() {
        Block jaxbDestination = new Block();
        Ast2JaxbHelper.setBasicProperties(this, jaxbDestination);

        Ast2JaxbHelper.addField(jaxbDestination, BlocklyConstants.POSITION, getMode().toString());
        Ast2JaxbHelper.addField(jaxbDestination, BlocklyConstants.MOTORPORT, getUserDefinedPort());
        return jaxbDestination;
    }
}
