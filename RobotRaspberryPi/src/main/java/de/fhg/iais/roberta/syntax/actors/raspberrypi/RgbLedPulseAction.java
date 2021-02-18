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
 * To create an instance from this class use the method {@link #make(String, Expr, Expr, Expr, Expr, Expr, BlocklyBlockProperties, BlocklyComment)}.<br>
 */
public class RgbLedPulseAction<V> extends Action<V> {
    private final String port;
    private final Expr<V> fadeInTime;
    private final Expr<V> fadeOutTime;
    private final Expr<V> numBlinks;
    private final Expr<V> onColor;
    private final Expr<V> offColor;

    private RgbLedPulseAction(
        String port,
        Expr<V> fadeInTime,
        Expr<V> fadeOutTime,
        Expr<V> onColor,
        Expr<V> offColor,
        Expr<V> numBlinks,
        BlocklyBlockProperties properties,
        BlocklyComment comment) {
        super(BlockTypeContainer.getByName("RGBLED_PULSE_ACTION"), properties, comment);
        Assert.notNull(port);
        Assert.notNull(fadeInTime);
        Assert.notNull(fadeOutTime);
        Assert.notNull(numBlinks);
        Assert.notNull(onColor);
        Assert.notNull(offColor);
        this.port = port;
        this.fadeInTime = fadeInTime;
        this.fadeOutTime = fadeOutTime;
        this.onColor = onColor;
        this.offColor = offColor;
        this.numBlinks = numBlinks;
        setReadOnly();
    }

    /**
     * Creates instance of {@link RgbLedPulseAction}. This instance is read only and can not be modified.
     *
     * @param properties of the block (see {@link BlocklyBlockProperties}),
     * @param comment added from the user,
     * @return read only object of class {@link RgbLedPulseAction}
     */
    private static <V> RgbLedPulseAction<V> make(
        String port,
        Expr<V> fadeInTime,
        Expr<V> fadeOutTime,
        Expr<V> onColor,
        Expr<V> offColor,
        Expr<V> numBlinks,
        BlocklyBlockProperties properties,
        BlocklyComment comment) {
        return new RgbLedPulseAction<>(port, fadeInTime, fadeOutTime, onColor, offColor, numBlinks, properties, comment);
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
        List<Value> values = helper.extractValues(block, (short) 5);

        Phrase<V> fadeInTime = helper.extractValue(values, new ExprParam(BlocklyConstants.FADE_IN_TIME, BlocklyType.NUMBER));
        Phrase<V> fadeOutTime = helper.extractValue(values, new ExprParam(BlocklyConstants.FADE_OUT_TIME, BlocklyType.NUMBER));
        Phrase<V> onColor = helper.extractValue(values, new ExprParam(BlocklyConstants.ON_COLOR, BlocklyType.COLOR));
        Phrase<V> offColor = helper.extractValue(values, new ExprParam(BlocklyConstants.OFF_COLOR, BlocklyType.COLOR));
        Phrase<V> duration = helper.extractValue(values, new ExprParam(BlocklyConstants.N_TIMES, BlocklyType.NUMBER_INT));
        String port = helper.extractField(fields, BlocklyConstants.ACTORPORT);
        return RgbLedPulseAction
            .make(
                port,
                helper.convertPhraseToExpr(fadeInTime),
                helper.convertPhraseToExpr(fadeOutTime),
                helper.convertPhraseToExpr(onColor),
                helper.convertPhraseToExpr(offColor),
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

    public Expr<V> getFadeInTime() {
        return this.fadeInTime;
    }

    public Expr<V> getFadeOutTime() {
        return this.fadeOutTime;
    }

    public Expr<V> getOnColor() {
        return this.onColor;
    }

    public Expr<V> getOffColor() {
        return this.offColor;
    }

    public Expr<V> getNumBlinks() {
        return this.numBlinks;
    }

    @Override
    public String toString() {
        return "LedSetAction [ " + this.port + ", " + this.fadeInTime + ", " + this.fadeOutTime + ", " + this.onColor + ", "
                + this.offColor + this.numBlinks + " ]";
    }

    @Override
    protected V acceptImpl(IVisitor<V> visitor) {
        return ((IRaspberryPiVisitor<V>) visitor).visitRgbLedPulseAction(this);
    }

    @Override
    public Block astToBlock() {
        Block jaxbDestination = new Block();
        Ast2JaxbHelper.setBasicProperties(this, jaxbDestination);

        Ast2JaxbHelper.addField(jaxbDestination, BlocklyConstants.ACTORPORT, this.port);
        Ast2JaxbHelper.addValue(jaxbDestination, BlocklyConstants.FADE_IN_TIME, this.fadeInTime);
        Ast2JaxbHelper.addValue(jaxbDestination, BlocklyConstants.FADE_OUT_TIME, this.fadeOutTime);
        Ast2JaxbHelper.addValue(jaxbDestination, BlocklyConstants.ON_COLOR, this.onColor);
        Ast2JaxbHelper.addValue(jaxbDestination, BlocklyConstants.OFF_COLOR, this.offColor);
        Ast2JaxbHelper.addValue(jaxbDestination, BlocklyConstants.N_TIMES, this.numBlinks);
        return jaxbDestination;

    }
}
