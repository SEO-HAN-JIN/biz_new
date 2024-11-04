const THEME_KEY = "theme";

function toggleDarkTheme() {
  setTheme(
      document.documentElement.getAttribute("data-bs-theme") === "dark"
          ? "light"
          : "dark"
  );
}

/**
 * Set theme for mazer
 * @param {"dark"|"light"} theme
 * @param {boolean} persist
 */
function setTheme(theme, persist = false) {
  document.body.classList.remove("light", "dark", "theme-light", "theme-dark");
  document.body.classList.add(theme, theme === 'dark' ? 'theme-dark' : 'theme-light');
  document.documentElement.setAttribute("data-bs-theme", theme);

  setGridTheme(theme); // TUI Grid 테마 적용

  if (persist) {
    localStorage.setItem(THEME_KEY, theme);
  }
}

/**
 * Init theme from setTheme()
 */
function initTheme() {
  const storedTheme = localStorage.getItem(THEME_KEY);
  if (storedTheme) {
    return setTheme(storedTheme);
  }

  const mediaQuery = window.matchMedia("(prefers-color-scheme: dark)");

  mediaQuery.addEventListener("change", (e) =>
      setTheme(e.matches ? "dark" : "light", true)
  );  
  return setTheme(mediaQuery.matches ? "dark" : "light", true);
}

window.addEventListener("DOMContentLoaded", () => {
  const toggler = document.getElementById("toggle-dark");
  const theme = localStorage.getItem(THEME_KEY);

  if (toggler) {
    toggler.checked = theme === "dark";

    toggler.addEventListener("input", (e) => {
      setTheme(e.target.checked ? "dark" : "light", true);
    });
  }
});

function setGridTheme(theme) {
  if (theme === 'dark') {
    tui.Grid.applyTheme('darkMode', {});
  } else {
    tui.Grid.applyTheme('presetDefault', {
    });
  }
}


initTheme();
