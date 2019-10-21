package mams.logic.commands;

import static mams.logic.parser.CliSyntax.PREFIX_REASON;

public abstract class ResolveCommand  extends Command {

    public static final String COMMAND_WORD_APPROVE_APPEAL = "approve";
    public static final String COMMAND_WORD_REJECT_APPEAL = "reject";

    public static final String MESSAGE_APPEAL_ALREADY_APPROVED = "The appeal was already resolved";

    public static final String MESSAGE_REJECT_ALREADY_REJECTED = "The appeal was already resolved";

}
