package core.model.gameplay.items;

public abstract class SpinnerThreesome {
    SpinnerNode dressed;
    SpinnerNode curr;

    public ItemRecord getNext() {
        setDressed();
        setValues();
        return dressed.next().value;
    }

    protected abstract void setDressed();
    protected abstract void setValues();
    protected abstract SpinnerNode getNode(ItemRecord itemRecord);

        protected class SpinnerNode {
            ItemRecord value;
            SpinnerNode next;

            public SpinnerNode() {
                curr = this;
            }

            SpinnerNode next() {
                curr = curr.next;
                if (curr.value != null) {
                    return curr;
                } else {
                    return curr = next();
                }
            }
        }

}
