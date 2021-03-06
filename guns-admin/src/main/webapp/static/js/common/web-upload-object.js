/**
 * web-upload 工具类
 *
 * 约定：
 * 上传按钮的id = 图片隐藏域id + 'BtnId'
 * 图片预览框的id = 图片隐藏域id + 'PreId'
 *
 * @author fengshuonan
 */
(function () {


        var $WebUpload = function (pictureId) {
            this.pictureId = pictureId;
            this.uploadBtnId = pictureId + "BtnId";
            this.uploadPreId = pictureId + "PreId";
            this.uploadUrl = Feng.ctxPath + '/mgr/upload';
            this.fileSizeLimit = 100 * 1024 * 1024;
            this.picWidth = 800;
            this.picHeight = 800;
            this.uploadBarId = null;
        };

        $WebUpload.prototype = {
            /**
             * 初始化webUploader
             */
            init: function () {
                var uploader = this.create();
                this.bindEvent(uploader);
                return uploader;
            },

            /**
             * 创建webuploader对象
             */
            create: function () {
                var webUploader = WebUploader.create({
                    auto: true,
                    pick: {
                        id: '#' + this.uploadBtnId,
                        multiple: false,// 只上传一个
                    },
                    accept: {
                        title: 'Images',
                        extensions: 'gif,jpg,jpeg,bmp,png',
                        mimeTypes: 'image/gif,image/jpg,image/jpeg,image/bmp,image/png'
                    },
                    swf: Feng.ctxPath
                    + '/static/css/plugins/webuploader/Uploader.swf',
                    disableGlobalDnd: true,
                    duplicate: true,
                    server: this.uploadUrl,
                    fileSingleSizeLimit: this.fileSizeLimit
                });

                return webUploader;
            },

            /**
             * 绑定事件
             */
            bindEvent: function (bindedObj) {
                var me = this;
                bindedObj.on('fileQueued', function (file) {
                    var $li = $('<div><img width="100px" height="100px"></div>');
                    var $img = $li.find('img');

                    $("#" + me.uploadPreId).html($li);

                    // 生成缩略图
                    bindedObj.makeThumb(file, function (error, src) {
                        if (error) {
                            $img.replaceWith('<span>不能预览</span>');
                            return;
                        }
                        $img.attr('src', src);
                    }, me.picWidth, me.picHeight);
                });

                // 文件上传过程中创建进度条实时显示。
                bindedObj.on('uploadProgress', function (file, percentage) {
                    $("#" + me.uploadBarId).css("width", percentage * 100 + "%");
                });

                // 文件上传成功，给item添加成功class, 用样式标记上传成功。
                bindedObj.on('uploadSuccess', function (file, response) {
                    Feng.success("上传成功");
                    $("#" + me.pictureId).val(response);
                });

                // 文件上传失败，显示上传出错。
                bindedObj.on('uploadError', function (file) {
                    Feng.error("上传失败");
                });

                // 其他错误
                bindedObj.on('error', function (type) {
                    if ("Q_EXCEED_SIZE_LIMIT" == type) {
                        Feng.error("文件大小超出了限制");
                    } else if ("Q_TYPE_DENIED" == type) {
                        Feng.error("文件类型不满足");
                    } else if ("Q_EXCEED_NUM_LIMIT" == type) {
                        Feng.error("上传数量超过限制");
                    } else if ("F_DUPLICATE" == type) {
                        Feng.error("图片选择重复");
                    } else {
                        Feng.error("上传过程中出错");
                    }
                });

                // 完成上传完了，成功或者失败
                bindedObj.on('uploadComplete', function (file) {
                });
            },

            /**
             * 设置图片上传的进度条的id
             */
            setUploadBarId: function (id) {
                this.uploadBarId = id;
            }
        };

        window.$WebUpload = $WebUpload;


        /*广告上传*/
        var $WebUploadAd = function (pictureId) {
            this.pictureId = pictureId;
            this.uploadBtnId = pictureId + "BtnId";
            this.uploadPreId = pictureId + "PreId";
            this.uploadUrl = Feng.ctxPath + '/mgr/upload';
            this.fileSizeLimit = 100 * 1024 * 1024;
            this.picWidth = 800;
            this.picHeight = 800;
            this.uploadBarId = null;
        };

        $WebUploadAd.prototype = {
            /**
             * 初始化webUploader
             */
            init: function () {
                var uploader = this.create();
                this.bindEvent(uploader);
                return uploader;
            },

            /**
             * 创建webuploader对象
             */
            create: function () {
                var webUploader = WebUploader.create({
                    auto: true,
                    pick: {
                        id: '#' + this.uploadBtnId,
                        multiple: false,// 只上传一个
                    },
                    accept: {
                        title: 'Images',
                        extensions: 'gif,jpg,jpeg,bmp,png',
                        mimeTypes: 'image/gif,image/jpg,image/jpeg,image/bmp,image/png'
                    },
                    swf: Feng.ctxPath
                    + '/static/css/plugins/webuploader/Uploader.swf',
                    disableGlobalDnd: true,
                    duplicate: true,
                    server: this.uploadUrl,
                    fileSingleSizeLimit: this.fileSizeLimit
                });

                return webUploader;
            },

            /**
             * 绑定事件
             */
            bindEvent: function (bindedObj) {
                var me = this;
                bindedObj.on('fileQueued', function (file) {
                    var $li = $('<div id="' + file.id + '" style="position:relative;float:left;" class="file-item thumbnail draggable-element">' +
                        '<a class="file-panel" href="javascript:;" style="position:absolute;top:10px;right:10px;">' +
                        '<span class="fa fa-close"></span></a>' +
                        '<img width="100px" height="100px">' +
                        '</div>');
                    var $img = $li.find('img');


                    var ads = $("#" + me.pictureId).val();
                    if (ads != '') {
                        $("#" + me.uploadPreId).append($li);
                    } else {
                        $("#" + me.uploadPreId).html($li);
                    }


                    // 生成缩略图
                    bindedObj.makeThumb(file, function (error, src) {
                        if (error) {
                            $img.replaceWith('<span>不能预览</span>');
                            return;
                        }
                        $img.attr('src', src);
                    }, me.picWidth, me.picHeight);
                });

                // 文件上传过程中创建进度条实时显示。
                bindedObj.on('uploadProgress', function (file, percentage) {
                    $("#" + me.uploadBarId).css("width", percentage * 100 + "%");
                });

                //删除
                $("#" + me.uploadPreId).on('click', '.file-panel', function () {
                    var fileItem = $(this).parent();

                    // var id = $(fileItem).attr("id");

                    // bindedObj.removeFile(id, true);

                    $(fileItem).fadeOut(function () {
                        $(fileItem).remove();
                        var ads = "";
                        $("#" + me.uploadPreId).find('img').each(function () {
                            if (ads != '') {
                                ads += ("," + $(this).attr("value"));
                            } else {
                                ads += ($(this).attr("value"));
                            }
                        });
                        $("#" + me.pictureId).val(ads);
                    });

                });

                // 文件上传成功，给item添加成功class, 用样式标记上传成功。
                bindedObj.on('uploadSuccess', function (file, response) {
                    var ads = $("#" + me.pictureId).val();
                    $("#" + me.uploadPreId + " #" + file.id).find('img').attr("value", response);
                    Feng.success("上传成功");
                    if (ads != '') {
                        $("#" + me.pictureId).val($("#" + me.pictureId).val() + "," + response);
                    } else {
                        $("#" + me.pictureId).val(response);
                    }

                });

                // 文件上传失败，显示上传出错。
                bindedObj.on('uploadError', function (file) {
                    Feng.error("上传失败");
                });

                // 其他错误
                bindedObj.on('error', function (type) {
                    if ("Q_EXCEED_SIZE_LIMIT" == type) {
                        Feng.error("文件大小超出了限制");
                    } else if ("Q_TYPE_DENIED" == type) {
                        Feng.error("文件类型不满足");
                    } else if ("Q_EXCEED_NUM_LIMIT" == type) {
                        Feng.error("上传数量超过限制");
                    } else if ("F_DUPLICATE" == type) {
                        Feng.error("图片选择重复");
                    } else {
                        Feng.error("上传过程中出错");
                    }
                });

                // 完成上传完了，成功或者失败
                bindedObj.on('uploadComplete', function (file) {
                });


            },

            /**
             * 设置图片上传的进度条的id
             */
            setUploadBarId: function (id) {
                this.uploadBarId = id;
            }
        };


        window.$WebUploadAd = $WebUploadAd;

    }
    ()
);