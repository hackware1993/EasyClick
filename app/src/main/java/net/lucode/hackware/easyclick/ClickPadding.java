package net.lucode.hackware.easyclick;

public class ClickPadding {
    private int leftClickPadding;
    private int rightClickPadding;
    private int topClickPadding;
    private int bottomClickPadding;

    public int getLeftClickPadding() {
        return leftClickPadding;
    }

    public void setLeftClickPadding(int leftClickPadding) {
        if (leftClickPadding < 0) {
            leftClickPadding = 0;
        }
        this.leftClickPadding = leftClickPadding;
    }

    public int getRightClickPadding() {
        return rightClickPadding;
    }

    public void setRightClickPadding(int rightClickPadding) {
        if (rightClickPadding < 0) {
            rightClickPadding = 0;
        }
        this.rightClickPadding = rightClickPadding;
    }

    public int getTopClickPadding() {
        return topClickPadding;
    }

    public void setTopClickPadding(int topClickPadding) {
        if (topClickPadding < 0) {
            topClickPadding = 0;
        }
        this.topClickPadding = topClickPadding;
    }

    public int getBottomClickPadding() {
        return bottomClickPadding;
    }

    public void setBottomClickPadding(int bottomClickPadding) {
        if (bottomClickPadding < 0) {
            bottomClickPadding = 0;
        }
        this.bottomClickPadding = bottomClickPadding;
    }
}
