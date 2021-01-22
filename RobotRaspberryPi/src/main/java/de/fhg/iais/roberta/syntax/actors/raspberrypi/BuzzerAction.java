package de.fhg.iais.roberta.syntax.actors.raspberrypi;

import java.util.List;

import de.fhg.iais.roberta.blockly.generated.Block;
import de.fhg.iais.roberta.blockly.generated.Field;
import de.fhg.iais.roberta.factory.BlocklyDropdownFactory;
import de.fhg.iais.roberta.inter.mode.action.IBuzzerMode;
import de.fhg.iais.roberta.mode.action.BrickLedColor;
import de.fhg.iais.roberta.syntax.*;
import de.fhg.iais.roberta.syntax.action.Action;
import de.fhg.iais.roberta.transformer.AbstractJaxb2Ast;
import de.fhg.iais.roberta.transformer.Ast2JaxbHelper;
import de.fhg.iais.roberta.util.dbc.Assert;
import de.fhg.iais.roberta.visitor.IVisitor;
import de.fhg.iais.roberta.visitor.hardware.IRaspberryPiVisitor;
import de.fhg.iais.roberta.visitor.hardware.actor.ILightVisitor;
import de.fhg.iais.roberta.visitor.hardware.actor.ISoundVisitor;

public class BuzzerAction<V> extends Action<V> {

    private final String port;
    private final IBuzzerMode mode;

    private BuzzerAction(String port, IBuzzerMode mode, BlocklyBlockProperties properties, BlocklyComment comment) {
        super(BlockTypeContainer.getByName("BUZZER_ACTION"), properties, comment);
        Assert.isTrue(mode != null);

        this.port = port;
        this.mode = mode;
        setReadOnly();
    }

    /**
     * Creates instance of {@link BuzzerAction}. This instance is read only and can not be modified.
     *
     * @param color of the lights on the brick. All possible colors are defined in {@link BrickLedColor}; must be <b>not</b> null,
     * @param properties of the block (see {@link BlocklyBlockProperties}),
     * @param comment added from the user,
     * @return read only object of class {@link BuzzerAction}
     */
    public static <V> BuzzerAction<V> make(String port, IBuzzerMode mode, BlocklyBlockProperties properties, BlocklyComment comment) {
        return new BuzzerAction<>(port, mode, properties, comment);
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
        String port = helper.extractField(fields, BlocklyConstants.ACTORPORT, BlocklyConstants.NO_PORT);
        String mode = helper.extractField(fields, BlocklyConstants.MODE, BlocklyConstants.DEFAULT);

        return BuzzerAction.make(factory.sanitizePort(port), factory.getBuzzerMode(mode), helper.extractBlockProperties(block), helper.extractComment(block));
    }

    /**
     * @return type of blinking.
     */
    public IBuzzerMode getMode() {
        return this.mode;
    }

    /**
     * @return port.
     */
    public String getPort() {
        return this.port;
    }

    @Override
    public String toString() {
        return "LightAction [" + this.port + ", " + this.mode + "]";
    }

    @Override
    protected V acceptImpl(IVisitor<V> visitor) {
        return ((IRaspberryPiVisitor<V>) visitor).visitBuzzerAction(this);
    }

    @Override
    public Block astToBlock() {
        Block jaxbDestination = new Block();
        Ast2JaxbHelper.setBasicProperties(this, jaxbDestination);
        Ast2JaxbHelper.addField(jaxbDestination, BlocklyConstants.MODE, getMode().toString());
        Ast2JaxbHelper.addField(jaxbDestination, BlocklyConstants.ACTORPORT, getPort().toString());

        return jaxbDestination;

    }
}