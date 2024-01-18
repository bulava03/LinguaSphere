var columnNumberSortedLast = -1;
var directionLast = 0;

function sortTable(columnIndex) {
  if (columnNumberSortedLast !== columnIndex) {
    directionLast = 1;
  } else {
    if (directionLast === 1) {
      directionLast = 2;
    } else {
      directionLast = 1;
    }
  }

  var table, rows, switching, i, x, y, shouldSwitch;
      table = document.getElementById("lessonsTable");
      switching = true;

      while (switching) {
        switching = false;
        rows = table.rows;
        for (i = 1; i < rows.length - 1; i++) {
          shouldSwitch = false;
          x = rows[i].getElementsByTagName("td")[columnIndex];
          y = rows[i + 1].getElementsByTagName("td")[columnIndex];

          if (directionLast === 1) {
            if (isNaN(x.innerHTML)) {
              shouldSwitch = x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase();
            } else {
              shouldSwitch = Number(x.innerHTML) > Number(y.innerHTML);
            }
          } else {
            if (isNaN(x.innerHTML)) {
              shouldSwitch = x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase();
            } else {
              shouldSwitch = Number(x.innerHTML) < Number(y.innerHTML);
            }
          }

          if (shouldSwitch) {
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
          }
          removeEmptyRows();
        }
      }

  columnNumberSortedLast = columnIndex;
}

function removeEmptyRows() {
  var table = document.getElementById("lessonsTable");
  var rows = table.getElementsByTagName("tr");

  for (var i = rows.length - 1; i >= 0; i--) {
    if (rows[i].textContent.trim() === "") {
      table.deleteRow(i);
    }
  }
}
