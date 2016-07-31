/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    loadDVDs();
    // populate date dropdown
    var dateDropdown = $('#add-year');
    var todaysDate = new Date();
    for (var i = todaysDate.getFullYear(); i >= 1880; i--) {
        dateDropdown.append($('<option>')
                .attr({
                    'value': i
                }).text(i));
    }

//add button functionality
    $('#add-button').on('click', function () {
        event.preventDefault();
        if ($('#add-title').val() === "") {
            $('#add-title-label').attr('class', 'hidden');
            $('#add-title-warning').attr('class', 'warning');
        } else {
            $.ajax({
                type: 'POST',
                url: 'dvd',
                data: JSON.stringify({
                    title: $('#add-title').val(),
                    releaseYear: $('#add-year').val(),
                    mpaaRating: $('#add-rating').val(),
                    studio: $('#add-studio').val(),
                    director: $('#add-director').val(),
                    note: $('#add-notes').val()
                }),
                contentType: 'application/json; charset=utf-8',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                dataType: 'json'
            }).success(function (data, status) {
                // clear add form and any validation errors
                $('#add-title').val('');
                $('#add-year option:eq(0)').prop('selected', true);
                $('#add-rating option:eq(2016)').prop('selected', true);
                $('#add-studio').val('');
                $('#add-director').val('');
                $('#add-notes').val('');
                $('#add-title-warning').attr('class', 'hidden');
                $('#add-title-label').removeClass('hidden');
                $('#addValidationErrors').empty();
                loadDVDs();
            }).error(function (data, status) {
                var errorDiv = $('#addValidationErrors');
                errorDiv.empty();
                $.each(data.responseJSON.fieldErrors, function (index, validationError) {
                    errorDiv.append(validationError.message).append($('<br>'));
                });
            });
        }
    });

    $('#edit-button').on('click', function () {
        event.preventDefault();
        if ($('#edit-title').val() === "") {
            $('#edit-title-label').attr('class', 'hidden');
            $('#edit-title-warning').attr('class', 'warning');
        } else {
            $.ajax({
                type: 'PUT',
                url: 'dvd/' + $('#edit-id').val(),
                data: JSON.stringify({
                    title: $('#edit-title').val(),
                    releaseYear: $('#edit-year').val(),
                    mpaaRating: $('#edit-rating').val(),
                    studio: $('#edit-studio').val(),
                    director: $('#edit-director').val(),
                    note: $('#edit-notes').val()
                }),
                contentType: 'application/json; charset=utf-8',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                dataType: 'json'
            }).success(function (data, status) {
                $('#editModal').modal('toggle');
                $('#edit-title-warning').attr('class', 'hidden');
                $('#edit-title-label').removeClass('hidden');
                $('#editValidationErrors').empty();
                loadDVDs();
            }).error(function (data, status) {
                var errorDiv = $('#editValidationErrors');
                errorDiv.empty();
                $.each(data.responseJSON.fieldErrors, function (index, validationError) {
                    errorDiv.append(validationError.message).append($('<br>'));
                });
            });
        }
    });
});

function loadDVDs() {
    $.ajax({
        url: 'all-dvds'
    }).success(function (data, status) {
        fillDVDList(data);
    });
}

function fillDVDList(dvds) {
// clear the previous list
    clearDVDList();
    var dvdList = $('#dvd-list');
    $.each(dvds, function (index, dvd) {
        dvdList.append($('<li>')
                .append($('<a>')
                        .attr({
                            'data-dvd-id': dvd.id,
                            'data-toggle': 'modal',
                            'data-target': '#detailsModal',
                        })
                        .text(dvd.title + " (" + dvd.releaseYear + ")"))
                .append($('<a>')
                        .attr({
                            'onClick': 'deleteDVD(' + dvd.id + ', "' + dvd.title + '")',
                            'class': 'align-right'
                        })
                        .append($('<i>')
                                .attr({
                                    'class': "fa fa-times",
                                    'aria-hidden': 'true'
                                }))
                        .append($('<span>')
                                .attr({
                                    'class': 'sr-only'
                                })
                                .text('Delete')))
                .append($('<a>')
                        .attr({
                            'data-dvd-id': dvd.id,
                            'data-toggle': 'modal',
                            'data-target': '#editModal',
                            'class': 'align-right'
                        })
                        .append($('<i>')
                                .attr({
                                    'class': "fa fa-pencil",
                                    'aria-hidden': 'true'
                                }))
                        .append($('<span>')
                                .attr({
                                    'class': 'sr-only'
                                })
                                .text('Edit'))));
    });
}


function clearDVDList() {
    $('#dvd-list').empty();
}

$('#detailsModal').on('show.bs.modal', function (event) {
    var element = $(event.relatedTarget);
    var dvdID = element.data('dvd-id');
    var modal = $(this);
    $.ajax({
        type: 'GET',
        url: 'dvd/' + dvdID
    }).success(function (dvd) {
        modal.find('#dvd-title').text(dvd.title);
        modal.find('#dvd-year').text(dvd.releaseYear);
        modal.find('#dvd-mpaaRating').text(dvd.mpaaRating);
        modal.find('#dvd-director').text(dvd.director);
        modal.find('#dvd-studio').text(dvd.studio);
        modal.find('#dvd-notes').text(dvd.note);
    });
});

$('#editModal').on('show.bs.modal', function (event) {
    var element = $(event.relatedTarget);
    var dvdID = element.data('dvd-id');
    var modal = $(this);
    $.ajax({
        type: 'GET',
        url: 'dvd/' + dvdID
    }).success(function (dvd) {
        // populate date dropdown
        var dateDropdown = $('#edit-year');
        populateYearDropdown(dateDropdown);

        modal.find('#edit-title').val(dvd.title);
        modal.find('#edit-year option[value="' + dvd.releaseYear + '"]').prop('selected', true);
        modal.find('#edit-rating option[value=' + dvd.mpaaRating + ']').prop('selected', true);
        modal.find('#edit-director').val(dvd.director);
        modal.find('#edit-studio').val(dvd.studio);
        modal.find('#edit-notes').val(dvd.note);
        modal.find('#edit-id').val(dvd.id);
    });
});

function deleteDVD(dvdID, dvdTitle) {
    var answer = confirm('Do you really want to delete ' + dvdTitle + '?');
    if (answer) {
        $.ajax({
            type: 'DELETE',
            url: 'dvd/' + dvdID
        }).success(function () {
            loadDVDs();
        });
    }
}

function populateYearDropdown(dropDownMenu) {
    var todaysDate = new Date();
    for (var i = todaysDate.getFullYear(); i >= 1880; i--) {
        dropDownMenu.append($('<option>')
                .attr({
                    'value': i
                }).text(i));
    }
}