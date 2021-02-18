package de.fhg.iais.roberta.syntax.actors.raspberrypi;

import java.util.List;

import de.fhg.iais.roberta.blockly.generated.Block;
import de.fhg.iais.roberta.blockly.generated.Field;
import de.fhg.iais.roberta.blockly.generated.Value;
import de.fhg.iais.roberta.syntax.*;
import de.fhg.iais.roberta.syntax.action.Action;
import de.fhg.iais.roberta.syntax.lang.expr.ColorConst;
import de.fhg.iais.roberta.syntax.lang.expr.Expr;
import de.fhg.iais.roberta.transformer.AbstractJaxb2Ast;
import de.fhg.iais.roberta.transformer.Ast2JaxbHelper;
import de.fhg.iais.roberta.transformer.ExprParam;
import de.fhg.iais.roberta.typecheck.BlocklyType;
import de.fhg.iais.roberta.util.dbc.Assert;
import de.fhg.iais.roberta.visitor.IVisitor;
import de.fhg.iais.roberta.visitor.hardware.IRaspberryPiVisitor;

/**
 * This class represents the <b>mbedActions_ledBar_set</b> blocks from Blockly into the AST (abstract syntax tree). Object from this class will generate code
 * for turning on the Grove LED Bar v2.0.<br/>
 * <br>
 * The client must provide the {@link ColorConst} color of the led. <br>
 * <br>
 * To create an instance from this class use the method {@link #make(String, Expr, Expr, Expr, BlocklyBlockProperties, BlocklyComment)}.<br>
 */
public class LedBlinkAction<V> extends Action<V> {
    private final String port;
    private final Expr<V> onTime;
    private final Expr<V> offTime;
    private final Expr<V> numBlinks;

    private LedBlinkAction(String port, Expr<V> onTime, Expr<V> offTime, Expr<V> numBlinks, BlocklyBlockProperties properties, BlocklyComment comment) {
        super(BlockTypeContainer.getByName("LED_BLINK_ACTION"), properties, comment);
        Assert.notNull(port);
        Assert.notNull(onTime);
        Assert.notNull(offTime);
        Assert.notNull(numBlinks);
        this.port = port;
        this.onTime = onTime;
        this.offTime = offTime;
        this.numBlinks = numBlinks;
        setReadOnly();
    }

    /**
     * Creates instance of {@link LedBlinkAction}. This instance is read only and can not be modified.
     *
     * @param properties of the block (see {@link BlocklyBlockProperties}),
     * @param comment added from the user,
     * @return read only object of class {@link LedBlinkAction}
     */
    private static <V> LedBlinkAction<V> make(
        String port,
        Expr<V> onTime,
        Expr<V> offTime,
        Expr<V> numBlinks,
        BlocklyBlockProperties properties,
        BlocklyComment comment) {
        return new LedBlinkAction<>(port, onTime, offTime, numBlinks, properties, comment);
    }

    /**
     * Transformation from JAXB object to corresponding AST object.
     *
     * @param block for transformation
     * @param helper class for making the transformation
     * @return corresponding AST object
     */
    public static <V> Phrase<V> jaxbToAst(Block block, AbstractJaxb2Ast<V> helper) {
        List<Field> fields = helper.extractFields(block, (short) 1);
        List<Value> values = helper.extractValues(block, (short) 3);

        Phrase<V> onTime = helper.extractValue(values, new ExprParam(BlocklyConstants.ON_TIME, BlocklyType.NUMBER));
        Phrase<V> offTime = helper.extractValue(values, new ExprParam(BlocklyConstants.OFF_TIME, BlocklyType.NUMBER));
        Phrase<V> duration = helper.extractValue(values, new ExprParam(BlocklyConstants.N_TIMES, BlocklyType.NUMBER_INT));
        String port = helper.extractField(fields, BlocklyConstants.ACTORPORT);
        return LedBlinkAction
            .make(
                port,
                helper.convertPhraseToExpr(onTime),
                helper.convertPhraseToExpr(offTime),
                helper.convertPhraseToExpr(duration),
                helper.extractBlockProperties(block),
                helper.extractComment(block));

    }

    /**
     * @return x of the pixel.
     */
    public String getPort() {
        return this.port;
    }

    public Expr<V> getOnTime() {
        return this.onTime;
    }

    public Expr<V> getOffTime() {
        return this.offTime;
    }

    public Expr<V> getNumBlinks() {
        return this.numBlinks;
    }

    @Override
    public String toString() {
        return "LedSetAction [ " + this.port + ", " + this.onTime + ", " + this.offTime + ", " + this.numBlinks + " ]";
    }

    @Override
    protected V acceptImpl(IVisitor<V> visitor) {
        return ((IRaspberryPiVisitor<V>) visitor).visitLedBlinkAction(this);
    }

    @Override
    public Block astToBlock() {
        Block jaxbDestination = new Block();
        Ast2JaxbHelper.setBasicProperties(this, jaxbDestination);

        Ast2JaxbHelper.addField(jaxbDestination, BlocklyConstants.ACTORPORT, this.port);
        Ast2JaxbHelper.addValue(jaxbDestination, BlocklyConstants.ON_TIME, this.onTime);
        Ast2JaxbHelper.addValue(jaxbDestination, BlocklyConstants.OFF_TIME, this.offTime);
        Ast2JaxbHelper.addValue(jaxbDestination, BlocklyConstants.N_TIMES, this.numBlinks);
        return jaxbDestination;

    }
}
