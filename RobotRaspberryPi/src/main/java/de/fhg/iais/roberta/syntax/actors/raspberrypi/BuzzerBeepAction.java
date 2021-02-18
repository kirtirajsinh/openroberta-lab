package de.fhg.iais.roberta.syntax.actors.raspberrypi;

import java.util.List;

import de.fhg.iais.roberta.blockly.generated.Block;
import de.fhg.iais.roberta.blockly.generated.Field;
import de.fhg.iais.roberta.blockly.generated.Value;
import de.fhg.iais.roberta.syntax.*;
import de.fhg.iais.roberta.syntax.action.Action;
import de.fhg.iais.roberta.syntax.lang.expr.Expr;
import de.fhg.iais.roberta.transformer.AbstractJaxb2Ast;
import de.fhg.iais.roberta.transformer.Ast2JaxbHelper;
import de.fhg.iais.roberta.transformer.ExprParam;
import de.fhg.iais.roberta.typecheck.BlocklyType;
import de.fhg.iais.roberta.util.dbc.Assert;
import de.fhg.iais.roberta.visitor.IVisitor;
import de.fhg.iais.roberta.visitor.hardware.IRaspberryPiVisitor;

/**
 * This class represents the <b>robactions_rasp_buzzer_beep</b> blocks from Blockly into the AST (abstract syntax tree). Object from this class will generate code
 * for making a sound on the buzzer.<br/>
 * <br>

 * <br>
 * To create an instance from this class use the method {@link #make(String, Expr, Expr, Expr, BlocklyBlockProperties, BlocklyComment)}.<br>
 */
public class BuzzerBeepAction<V> extends Action<V> {
    private final String port;
    private final Expr<V> onTime;
    private final Expr<V> offTime;
    private final Expr<V> numBeeps;

    private BuzzerBeepAction(String port, Expr<V> onTime, Expr<V> offTime, Expr<V> numBeeps, BlocklyBlockProperties properties, BlocklyComment comment) {
        super(BlockTypeContainer.getByName("BUZZER_BEEP_ACTION"), properties, comment);
        Assert.notNull(port);
        Assert.notNull(onTime);
        Assert.notNull(offTime);
        Assert.notNull(numBeeps);
        this.port = port;
        this.onTime = onTime;
        this.offTime = offTime;
        this.numBeeps = numBeeps;
        setReadOnly();
    }

    /**
     * Creates instance of {@link BuzzerBeepAction}. This instance is read only and can not be modified.
     *
     * @param properties of the block (see {@link BlocklyBlockProperties}),
     * @param comment added from the user,
     * @return read only object of class {@link BuzzerBeepAction}
     */
    private static <V> BuzzerBeepAction<V> make(
        String port,
        Expr<V> onTime,
        Expr<V> offTime,
        Expr<V> numBeeps,
        BlocklyBlockProperties properties,
        BlocklyComment comment) {
        return new BuzzerBeepAction<>(port, onTime, offTime, numBeeps, properties, comment);
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
        return BuzzerBeepAction
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

    public Expr<V> getNumBeeps() {
        return this.numBeeps;
    }

    @Override
    public String toString() {
        return "LedSetAction [ " + this.port + ", " + this.onTime + ", " + this.offTime + ", " + this.numBeeps + " ]";
    }

    @Override
    protected V acceptImpl(IVisitor<V> visitor) {
        return ((IRaspberryPiVisitor<V>) visitor).visitBuzzerBeepAction(this);
    }

    @Override
    public Block astToBlock() {
        Block jaxbDestination = new Block();
        Ast2JaxbHelper.setBasicProperties(this, jaxbDestination);

        Ast2JaxbHelper.addField(jaxbDestination, BlocklyConstants.ACTORPORT, this.port);
        Ast2JaxbHelper.addValue(jaxbDestination, BlocklyConstants.ON_TIME, this.onTime);
        Ast2JaxbHelper.addValue(jaxbDestination, BlocklyConstants.OFF_TIME, this.offTime);
        Ast2JaxbHelper.addValue(jaxbDestination, BlocklyConstants.N_TIMES, this.numBeeps);
        return jaxbDestination;

    }
}
