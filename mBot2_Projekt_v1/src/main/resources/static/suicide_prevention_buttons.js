function toggleState(state) {
    const buttons = document.querySelectorAll('button[name="prevention"]');
    buttons.forEach(button => {
        if (button.value === state) {
            button.classList.add('on');
        } else {
            button.classList.remove('on');
        }
    });
}