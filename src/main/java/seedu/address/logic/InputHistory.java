package seedu.address.logic;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Manages input history of the command line
 */
public class InputHistory {
    public static final int MAX_SIZE = 16;
    private final Deque<String> back;
    private final Stack<String> front;

    /**
     * Creates a {@code InputHistory} with blank history
     */
    public InputHistory() {
        this.back = new LinkedList<>();
        this.front = new Stack<>();
    }

    /**
     * Returns the currently pointed command.
     *
     * @return The pointed command (May be null)
     */
    public String navigateCurrent() {
        if (front.isEmpty()) {
            return null;
        } else {
            return front.peek();
        }
    }

    /**
     * Shifts the history one command back if there are commands behind and returns the newly pointed command,
     * else returns the currently pointed command.
     *
     * @return The pointed command (May be null)
     */
    public String navigateBackward() {
        if (back.isEmpty()) {
            return this.navigateCurrent();
        } else {
            return front.push(back.removeFirst());
        }
    }

    /**
     * Shifts the history one command forward if there are commands in front and returns the newly pointed command,
     * else returns the currently pointed command.
     *
     * @return The pointed command (May be null)
     */
    public String navigateForward() {
        if (front.size() < 2) {
            return this.navigateCurrent();
        } else {
            back.push(front.pop());
            return front.peek();
        }
    }

    /**
     * Shifts the history to the command before {@code commandText} was entered, then adds {@code commandText}
     * to history. If the history is over {@link InputHistory.MAX_SIZE}, the oldest entry is deleted.
     *
     * @param commandText The new command to add to history
     */
    public void enterCommand(String commandText) {
        while (!front.isEmpty()) {
            back.push(front.pop());
        }
        front.push(commandText);

        if (back.size() > MAX_SIZE - 1) {
            back.removeLast();
        }
    }

    /**
     * Returns a proxy class for easy navigation of the history
     *
     * @return The proxy
     */
    public HistoryNavigator getNavigator() {
        return new HistoryNavigator() {
            @Override
            public String current() {
                return InputHistory.this.navigateCurrent();
            }

            @Override
            public String backward() {
                return InputHistory.this.navigateBackward();
            }

            @Override
            public String forward() {
                return InputHistory.this.navigateForward();
            }
        };
    }
}
