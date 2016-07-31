<!DOCTYPE html>
<html>
    <head>
        <title>DVD Library</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="${pageContext.request.contextPath}/css/starter-template.css" rel="stylesheet">

        <!-- FA Icon -->
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon_film.png">
        <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <div class="header">
                <h1><i class="fa fa-film"></i>DVD Library</h1>
            </div>

            <div class ="body">
                <div class="col-md-6">
                    <h2>My DVDs</h2>
                    <ul id="dvd-list" class="list-unstyled">
                    </ul>
                </div>
                <div class="new-dvd-form col-md-6">
                    <h2>Add a New DVD</h2>
                    <div id="addValidationErrors" class="warning"></div>
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="add-title" class="col-md-4 control-label">
                                <span id ="add-title-label">*Title</span>
                                <span id="add-title-warning" class="hidden col-md-4 col-md-offset-1">Please enter a Title.</span>
                            </label>
                            <div class="col-md-8">
                                <input type="text" maxlength="50" id="add-title" class="form-control" placeholder="50 characters max"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-year" class="col-md-4 control-label">Release Year</label>
                            <div class="col-md-8">
                                <select id="add-year" class="form-control">
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-rating" class="col-md-4 control-label">MPAA-Rating</label>
                            <div class="col-md-8">
                                <select id="add-rating" class="form-control">
                                    <option value="G">G</option>
                                    <option value="PG">PG</option>
                                    <option value="PG-13">PG-13</option>
                                    <option value="R">R</option>
                                    <option value="NC-17">NC-17</option>
                                    <option value="Not Rated">Not Rated</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-studio" class="col-md-4 control-label">Studio</label>
                            <div class="col-md-8">
                                <input type="text" maxlength="50" id="add-studio" class="form-control" placeholder="50 characters max">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-director" class="col-md-4 control-label">Director</label>
                            <div class="col-md-8">
                                <input type="text" maxlength="50" id="add-director" class="form-control" placeholder="50 characters max"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-notes" class="col-md-4 control-label">Notes</label>
                            <div class="col-md-8">
                                <textarea rows="3" maxlength="250" id="add-notes"  class="form-control" placeholder="250 characters max"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <button type="submit" id="add-button" class="btn bg-primary">
                                    Add DVD
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <div id="detailsModal" class="modal fade" role="dialog" aria-labelledby="detailsModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">
                                <span aria-hidden="true">&times;</span>
                                <span class="sr-only">Close</span>
                            </button>
                            <h4 class="modal-title" id="detailsModalLabel">DVD Details</h4>
                        </div>
                        <div class="modal-body">
                            <table class ="table">
                                <caption id="dvd-title"></caption>
                                <tr>
                                    <th>Release Year</th>
                                    <td id="dvd-year"></td>
                                </tr>
                                <tr>
                                    <th>MPAA-Rating</th>
                                    <td id="dvd-mpaaRating"></td>
                                </tr>
                                <tr>
                                    <th>Studio</th>
                                    <td id="dvd-studio"></td>
                                </tr>
                                <tr>
                                    <th>Director</th>
                                    <td id="dvd-director"></td>
                                </tr>
                                <tr>
                                    <th>Notes</th>
                                    <td id="dvd-notes"></td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <div id="editModal" class="modal fade" role="dialog" aria-labelledby="editModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">
                                <span aria-hidden="true">&times;</span>
                                <span class="sr-only">Close</span>
                            </button>
                            <h4 class="modal-title" id="editModalLabel">Edit a DVD</h4>
                        </div>
                        <div class="modal-body">
                            <form class="form-horizontal" role="form">
                                <div id="editValidationErrors" class="warning"></div>
                                <div class="form-group">
                                    <label for="edit-title" class="col-md-4 control-label">
                                        <span id ="edit-title-label">*Title</span>
                                        <span id="edit-title-warning" class="hidden col-md-4 col-md-offset-1">Please enter a Title.</span>
                                    </label>
                                    <p id="edit-title-warning" class="hidden">Please enter a title.</p>
                                    <div class="col-md-8">
                                        <input type="text" maxlength="50" id="edit-title" class="form-control" placeholder="50 characters max"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="edit-year" class="col-md-4 control-label">Release Year</label>
                                    <div class="col-md-8">
                                        <select id="edit-year" class="form-control">
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="edit-rating" class="col-md-4 control-label">MPAA-Rating</label>
                                    <div class="col-md-8">
                                        <select id="edit-rating" class="form-control">
                                            <option value="G">G</option>
                                            <option value="PG">PG</option>
                                            <option value="PG-13">PG-13</option>
                                            <option value="R">R</option>
                                            <option value="NC-17">NC-17</option>
                                            <option value="Not Rated">Not Rated</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="edit-studio" class="col-md-4 control-label">Studio</label>
                                    <div class="col-md-8">
                                        <input type="text" maxlength="50" id="edit-studio" class="form-control" placeholder="50 characters max">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="edit-director" class="col-md-4 control-label">Director</label>
                                    <div class="col-md-8">
                                        <input type="text" maxlength="50" id="edit-director" class="form-control" placeholder="50 characters max"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="edit-notes" class="col-md-4 control-label">Notes</label>
                                    <div class="col-md-8">
                                        <textarea rows="3" maxlength="250" id="edit-notes"  class="form-control" placeholder="250 characters max"></textarea>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-offset-4 col-md-8">
                                        <button type="submit" id="edit-button" class="btn bg-primary">
                                            Edit DVD
                                        </button>                                        
                                        <button type="button" class="btn btn-default" data-dismiss="modal">
                                            Cancel
                                        </button>
                                        <input type="hidden" id="edit-id" />
                                    </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="https://use.fontawesome.com/67eb5fbabd.js"></script>
        <script src="${pageContext.request.contextPath}/js/dvdLibrary.js"></script>
    </body>
</html>

