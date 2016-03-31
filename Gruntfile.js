var path = require('path');

module.exports = function(grunt) {

  grunt.registerTask('build', 'build the module', function() {

    var done = this.async();

    // runt ant to build the module
    var ant = grunt.util.spawn({
      cmd: 'ant',
      opts: {
        cwd: 'android'
      }
    }, function(error, result, code) {
      if(error) {
        grunt.fail.warn(error, code);
      } else {
        grunt.log.ok('build successful');
      }
      done(error, result);
    });
    ant.stdout.on('data', grunt.log.write);
    ant.stderr.on('data', grunt.log.error);

  });
  
  grunt.registerTask('deploy', 'install the module', function() {
    
    var done = this.async();
    var version = grunt.file.readJSON('package.json').version;
    var modPath = path.join(__dirname, 'android/dist/com.tripvi.drawerlayout-android-'+version+'.zip');
    
    var install = grunt.util.spawn({
      cmd: 'gittio',
      args: ['install', '-g', modPath]
    }, function(error, result, code) {
      if(error) {
        grunt.fail.warn(error, code);
      } else {
        grunt.log.ok('installed');
      }
      done(error, result);
    });
    install.stdout.on('data', grunt.log.write);
    install.stderr.on('data', grunt.log.error);
  });
  
  grunt.registerTask('version', 'update version from package.json', function() {

    // read files
    var version = grunt.file.readJSON('package.json').version;
    var manifest = grunt.file.read('android/manifest');
    var readme = grunt.file.read('README.md');

    // update manifest
    grunt.file.write('android/manifest', manifest.replace(/^version.*$/m, "version: " + version));

    // update readme
    grunt.file.write('README.md', readme.replace(/gittio-[\d\.]{2,}-00B4CC\.svg/g, "gittio-" + version + "-00B4CC.svg"));

  });

  grunt.registerTask('default', ['version', 'build', 'deploy']);

};